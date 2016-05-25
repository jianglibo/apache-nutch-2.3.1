package provide CommonUtil 1.0

namespace eval CommonUtil {

}

proc ::CommonUtil::whereami {{content {}}} {
  set fn [file join $::baseDir scripts whereami.txt]
  if {[string length $content] > 0} {
    writeContent $fn $content
  } else {
    return [readContent $fn]
  }
}

proc ::CommonUtil::readContent {fn} {
  if {[catch {open $fn} fid o]} {
    puts $fid
    exit 0
  } else {
    set data [string trim [read $fid]]
    close $fid
    return $data
  }
}

proc ::CommonUtil::writeContent {fn content} {
  if {[catch {open $fn w} fid o]} {
    puts $fid
    exit 0
  } else {
    puts $fid $content
    close $fid
  }
}