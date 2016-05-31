#!/bin/sh
# install-java.tcl \
exec tclsh "$0" ${1+"$@"}

set ::baseDir [file join [file dirname [info script]] ..]
lappend auto_path [file join $::baseDir scripts]

set paramlist [list]
set optionDict [dict create]
foreach a $argv {
  set pair [split $a =]
  if {[llength $pair] == 2} {
    dict set optionDict [string trimleft [lindex $pair 0] -] [lindex $pair 1]
  } else {
    lappend paramlist $a
  }
}

set deployDir [file normalize [file join $::baseDir runtime deploy]]
set crawlCmd [file join $deployDir bin crawl]
set nutchCmd [file join $deployDir bin nutch]

package require YamlUtil
package require CommonUtil

set mysiteyml [file join $::baseDir conf mysite.yml]
set ymlDict [::YamlUtil::loadYaml $mysiteyml]
set seedFolder [dict get $ymlDict seedFolder]
if {[file pathtype $seedFolder] eq {relative}} {
  set seedFolder [file join /user [exec whoami] $seedFolder]
}

if {! [dict exists $optionDict skipClass]} {
  puts "start running class"
  set preRunClasses [dict get $ymlDict preRunClasses]
  foreach c $preRunClasses {
    ::CommonUtil::spawnCommand $nutchCmd {*}[concat $c $paramlist]
  }
}
puts "start crawl......."
set start [clock milliseconds]
::CommonUtil::spawnCommand $crawlCmd $seedFolder [dict get $ymlDict crawlID] [dict get $ymlDict numberOfRounds]
puts "crawl done. costs: [expr ([clock milliseconds] - $start) / 60000] minuts."
