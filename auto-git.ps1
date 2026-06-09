$watchPath = "C:\Users\lenovo\Desktop\git"
$watcher = New-Object System.IO.FileSystemWatcher
$watcher.Path = $watchPath
$watcher.IncludeSubdirectories = $true
$watcher.EnableRaisingEvents = $true

$action = {
    Start-Sleep -Seconds 10
    Set-Location -LiteralPath "C:\Users\lenovo\Desktop\git"
    git add -A
    $status = git status --porcelain
    if ($status) {
        $msg = "auto commit $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"
        git commit -m $msg
        git push
    }
}

Register-ObjectEvent $watcher "Changed" -Action $action | Out-Null
Register-ObjectEvent $watcher "Created" -Action $action | Out-Null
Register-ObjectEvent $watcher "Deleted" -Action $action | Out-Null
Register-ObjectEvent $watcher "Renamed" -Action $action | Out-Null

Write-Host "自动监控已启动，按 Ctrl+C 停止..."
while ($true) { Start-Sleep -Seconds 1 }
