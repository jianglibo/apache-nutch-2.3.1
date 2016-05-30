#!/bin/sh
# install-java.tcl \
exec tclsh "$0" ${1+"$@"}

set ::baseDir [file join [file dirname [info script]] ..]
lappend auto_path [file join $::baseDir scripts]

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

set preRunClasses [dict get $ymlDict preRunClasses]

foreach c $preRunClasses {
  ::CommonUtil::spawnCommand $nutchCmd {*}[concat $c $argv]
}

puts "crawl $seedFolder [dict get $ymlDict crawlID] [dict get $ymlDict numberOfRounds]"

exit 0
set actions [list]
set rawParamDict [dict create]

foreach a $::argv {
  set pair [split $a =]
  if {[llength $pair] == 2} {
    dict set rawParamDict [string trimleft [lindex $pair 0] -] [lindex $pair 1]
  } else {
    lappend actions $a
  }
}

if {[llength $actions] == 0} {
  puts "need action parameter."
  exit 0
}

set action [lindex $actions 0]

switch -exact -- $action {
  switchSite {
    package require SiteSwitcher
    ::SiteSwitcher::switchSite $rawParamDict $actions
  }
  default {}
}
