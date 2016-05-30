package provide YamlUtil 1.0
package require yaml

namespace eval ::YamlUtil {
}

proc ::YamlUtil::loadYaml {fileName} {
  if {[catch {set dt [::yaml::yaml2dict -file $fileName]} msg o]} {
    puts $msg
    exit 0
  } else {
    return $dt
  }
}

proc ::YamlUtil::findValue {ymlDict key rawParamDict} {
  if {[dict exists $ymlDict $key]} {
    return [dict get $ymlDict $key]
  }
  set myNodes [getHostYmlNodes $ymlDict $rawParamDict]
  foreach node $myNodes {
    if {[dict exists $node $key]} {
      return [dict get $node $key]
    }
  }
  return {}
}
