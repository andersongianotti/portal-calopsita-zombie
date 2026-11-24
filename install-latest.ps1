# Baixa o jar mais recente do GitHub Releases e copia para a pasta mods do Minecraft
$repo = "andersongianotti/portal-calopsita-zombie"
$jar = "calopsita-portal-1.0.0.jar"
$mods = "$env:APPDATA\.minecraft\mods"
$url = "https://github.com/$repo/releases/latest/download/$jar"
Write-Host "Baixando $jar de $repo ..."
Invoke-WebRequest -Uri $url -OutFile (Join-Path $mods $jar)
Write-Host "Pronto! $jar instalado em $mods"
