#!/bin/sh
# install-java.tcl \
exec tclsh "$0" ${1+"$@"}

set ::baseDir [file join [file dirname [info script]] ..]
lappend auto_path [file join $::baseDir scripts]

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
