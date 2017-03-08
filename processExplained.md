. .\ps\switch-site.ps1
ant runtime
copy runtime/deploy/xx.job to boot-template project's jobproperties folder.


conf/nutch-site.xml, configure gora.
ivy/ivy.xml, <dependency org="org.apache.gora" name="gora-hbase" rev="0.6.1" conf="*->default" />
conf/gora.properties, gora.datastore.default=org.apache.gora.hbase.store.HBaseStore
conf/regex-urlfilter.txt