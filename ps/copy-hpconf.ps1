Param(
    [parameter(Mandatory=$true)][ValidateSet("home", "office")][string]$where,
    [parameter(Mandatory=$true)] $site
)

$projectRoot = $PSCommandPath | Split-Path -Parent | Split-Path -Parent 

$targetFolder = $projectRoot | Join-Path -ChildPath "conf-site\${where}\$site\"

if (-not (Test-Path $targetFolder)) {
    Write-Error "${targetFolder} doesn't exists."
}

$hadoopHome = Get-Item env:HADOOP_HOME -ErrorAction SilentlyContinue

if (!$hadoopHome) {
    Write-Error "please invoke 'setx HADOOP_HOME xx' on powershell commond line first."
}

$hadoopHome = $hadoopHome | Select-Object -ExpandProperty Value


$hbaseHome = Get-Item env:HBASE_HOME -ErrorAction SilentlyContinue

if (!$hbaseHome) {
    Write-Host "please invoke 'setx HBASE_HOME xx' on powershell commond line first."
    return
}


$hadoopFiles = ,"core-site.xml"
$hadoopFiles | ForEach-Object {Join-Path $hadoopHome -ChildPath "etc\hadoop\$_"} | Copy-Item -Destination $targetFolder

$hbaseFiles = ,"hbase-site.xml"
$hbaseFiles | ForEach-Object {Join-Path $hbaseHome -ChildPath "conf\$_"} | Copy-Item -Destination $targetFolder