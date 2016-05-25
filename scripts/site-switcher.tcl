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
  exec cp -f {*}[glob -directory $confDefault -type f *] $conf
  exec cp -f {*}[glob -directory $confSite -type f *] $conf

  ::CommonUtil::whereami $siteId
  puts "switch to $siteId done."
}
