Param(
    [parameter(Mandatory=$true)][ValidateSet("home", "office")][string]$where,
    [parameter(Mandatory=$true)] $site
)

$projectRoot = $PSCommandPath | Split-Path -Parent | Split-Path -Parent 

$targetConf = $projectRoot | Join-Path -ChildPath conf
if (Test-Path $targetConf) {
    Remove-Item -Recurse $targetConf -Force
}

$defaultConf = $projectRoot | Join-Path -ChildPath "conf-default"

$siteConf = $projectRoot | Join-Path -ChildPath "conf-site\${where}\$site\"
$siteConf | Write-Host
if (-not (Test-Path $siteConf)) {
    Write-Error "${siteConf} doesn't exists."
}

Copy-Item -Path $defaultConf  -Destination $targetConf -Recurse -Force

Get-ChildItem -Path $siteConf | Copy-Item -Destination $targetConf -Force

"switch to '${where} ${site}' done!" | Write-Host