#!/bin/sh
# install-java.tcl \
exec tclsh "$0" ${1+"$@"}

# this script is a special case, It construct all urls by hand. 

# if you do not inject new urls, crawl will only fetch reach interval items.
set ::baseDir [file join [file dirname [info script]] ..]
lappend auto_path [file join $::baseDir scripts]

set paramlist [list]
set optionDict [dict create]
foreach a $argv {
  set pair [split $a =]
  if {[llength $pair] == 2} {
    dict set optionDict [string trimleft [lindex $pair 0] -] [lindex $pair 1]
  } else {
    if {[string first - $a] != -1} {
      dict set optionDict [string trimleft $a -] 1
    } else {
      lappend paramlist $a
    }
  }
}
if {[llength $paramlist] == 0} {
  puts "action is mandtory."
  exit 0
}

set action [lindex $paramlist 0]
set paramlist [lrange $paramlist 1 end]

set deployDir [file normalize [file join $::baseDir runtime deploy]]
set crawlCmd [file join $deployDir bin crawl]
set nutchCmd [file join $deployDir bin nutch]

package require YamlUtil
package require CommonUtil

set crawlCmdNoInject [file join $deployDir bin crawl-noinject]
if {! [file exists $crawlCmdNoInject]} {
  set lines [::CommonUtil::readLines $crawlCmd]

  if {[catch {open $crawlCmdNoInject w} fid o]} {
    puts $fid
    exit 0
  } else {
    foreach line $lines {
      if {[string first "__bin_nutch inject" $line] == 0} {
        puts "skip inject."
      } elseif {[string match *Injecting* $line]} {
        puts $fid {echo "Skipping Injecting seed URLs."}
      } else {
        puts $fid $line
      }
    }
    close $fid
    exec chmod a+x $crawlCmdNoInject
  }
}

set mysiteyml [file join $::baseDir conf mysite.yml]
set ymlDict [::YamlUtil::loadYaml $mysiteyml]
set seedFolder [dict get $ymlDict seedFolder]
if {[file pathtype $seedFolder] eq {relative}} {
  set seedFolder [file join /user [exec whoami] $seedFolder]
}

proc printDone {msg start} {
  puts "$msg done. costs: [expr ([clock milliseconds] - $start) / 60000] minuts."
}

set start [clock milliseconds]
switch -exact -- $action {
  prepareSeed {
    puts "start $nutchCmd $c $paramlist"
    set preRunClasses [dict get $ymlDict preRunClasses]
    foreach c $preRunClasses {
      ::CommonUtil::spawnCommand $nutchCmd {*}[concat $c $paramlist]
    }
    printDone prepareSeed $start
  }
  inject {
    ::CommonUtil::spawnCommand $nutchCmd inject $seedFolder -crawlId [dict get $ymlDict crawlID]
    printDone inject $start
  }
  generate {
    # scan all table, found which need to refetch.
    ::CommonUtil::spawnCommand $nutchCmd generate -crawlId [dict get $ymlDict crawlID]
    printDone generate $start
  }
  fetch {
    ::CommonUtil::spawnCommand $nutchCmd fetch -all -crawlId [dict get $ymlDict crawlID]
    printDone fetch $start
  }
  parse {
    ::CommonUtil::spawnCommand $nutchCmd parse -all -crawlId [dict get $ymlDict crawlID]
    printDone parse $start
  }
  updatedb {
    ::CommonUtil::spawnCommand $nutchCmd updatedb -all -crawlId [dict get $ymlDict crawlID]
    printDone updatedb $start
  }
  crawl {
    ::CommonUtil::spawnCommand $crawlCmdNoInject $seedFolder [dict get $ymlDict crawlID] [dict get $ymlDict numberOfRounds]
    printDone crawl $start
  }
  default {
    puts "unknown action."
  }
}
