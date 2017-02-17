Param(
    [parameter(Mandatory=$true)][string]$VarName,
    [parameter(Mandatory=$true)][string]$XmlFilePath,
    [parameter(Mandatory=$true)][string]$OutFileName="xmltojava.txt"
)
$projectRoot = $PSCommandPath | Split-Path -Parent | Split-Path -Parent 

#[xml]$nutchXml = Get-Content ($projectRoot | Join-Path -ChildPath "conf" | Join-Path -ChildPath "nutch-default.xml")
[xml]$nutchXml = Get-Content $XmlFilePath
$outFile = $projectRoot | Join-Path -ChildPath $OutFileName

$nutchXml.configuration.childNodes |
 Where-Object {$_.name -ne "#comment"} |
# ForEach-Object {"private String {0} = `"{1}`";" -f ($_.name -replace "\.","9"),$_.value}
 ForEach-Object {"${VarName}.put(`"{0}`",`"{1}`");" -f $_.name ,$_.value} |
 Out-File $outFile -Encoding ascii