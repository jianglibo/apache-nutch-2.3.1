package provide CommonUtil 1.0

package require Expect

namespace eval CommonUtil {

}

proc ::CommonUtil::spawnCommand {args} {
  set timeout 100000
  spawn {*}$args
  expect {
    -re (.*\r) {
      puts "$expect_out(buffer)"
      exp_continue
    }
    eof {
      puts done
    }
    timeout {
      puts timeout
    }
  }
}

proc ::CommonUtil::whereami {{content {}}} {
  set fn [file join $::baseDir scripts whereami.txt]
  if {! [file exists $fn]} {
    exec touch $fn
  }
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

proc ::CommonUtil::readLines {fn} {
  set lines [list]
  if {[catch {open $fn} fid o]} {
    puts $fid
    exit 0
  } else {
    while {[gets $fid line] >= 0} {
      lappend lines $line
    }
    close $fid
    return $lines
  }
}

proc ::CommonUtil::writeLines {fn lines} {
  if {[catch {open $fn w} fid o]} {
    puts $fid
    exit 0
  } else {
    foreach line $lines {
      puts $fid $line
    }
    close $fid
  }
}
