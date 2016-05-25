package provide SiteSwitcher 1.0

package require CommonUtil

namespace eval SiteSwitcher {
}

proc ::SiteSwitcher::switchSite {rawParamDict actions} {
  if {[llength $actions] != 3} {
    puts "wrong command. must be: 'switchSite office nbgov' like."
    exit 0
  }
  set confSite [file normalize [file join $::baseDir conf-site [lindex $actions 1] [lindex $actions 2]]]
  if {! [file exists $confSite]} {
    puts "wrong site, please check conf-site folder."
    exit 0
  }

  set siteId [join $actions ,]
  set conf [file normalize [file join $::baseDir conf]]

  if {[::CommonUtil::whereami] eq $siteId} {
    if {[file exists $conf]} {
      if {[llength [glob -nocomplain -directory $conf -type f *]] > 0} {
        puts "alreay in $siteId"
        exit 0
      }
    }
  }

  set confDefault [file normalize [file join $::baseDir conf-default]]

  if {! [file exists $conf]} {
    exec mkdir -p $conf
  }
  exec rm -f {*}[glob -nocomplain -directory $conf -type f *]
  set filesToCopy [glob -nocomplain -directory $confDefault -type f *]
  if {[llength $filesToCopy] > 0} {
    exec cp -f {*}$filesToCopy $conf
  }
  set filesToCopy [glob -nocomplain -directory $confSite -type f *]
  if {[llength $filesToCopy] > 0} {
    exec cp -f {*}$filesToCopy $conf
  }

  ::CommonUtil::whereami $siteId
  puts "switch to $siteId done."
}
