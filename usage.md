## Usage

* switch site: ./script/util.tcl switchSite office nbgov
* build runtime: ant runtime
* prepare seed: ./script/nutch-runner.tcl prepareSeed
* run crawl: ./script/nutch-runner.tcl [--skipClass=true] [--classOnly=true] fetch
