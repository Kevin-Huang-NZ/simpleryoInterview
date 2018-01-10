[string]$libsPath = 'D:\program\ps\libs\'
[string]$basePath = 'D:\program\ps\nunu\'
[string]$logFile = -Join($basePath, 'grab.log')
[string]$srcUrl = -Join($basePath, 'source-url.txt')
[string]$listPagePath = -Join($basePath, 'listPages\')
[string]$goodsFileName = 'goods.txt'
[string]$titleFileName = 'title.txt'
[string]$imageFileName = 'image.txt'
[string]$detailImageFileName = 'detail-image.txt'
[string]$desciptionFolder = -Join($basePath, 'product-desc')
[string]$desciptionFileName = 'desc.txt'



[string]$libPath1 = -Join($libsPath,'HtmlAgilityPack.1.4.9.5\lib\Net45\HtmlAgilityPack.dll')
Add-Type -Path $libPath1

function Download-File() {
    Param([Parameter(
        Mandatory = $true,
        ParameterSetName = '',
        ValueFromPipeline = $false)]
        [string]$url,
        [Parameter(
        Mandatory = $true,
        ParameterSetName = '',
        ValueFromPipeline = $false)]
        [string]$outFile
        )

    Try {

        if (Test-Path -Path $outFile) {
            Remove-Item $outFile
        }

        Invoke-WebRequest $url -OutFile $outFile
        -Join('[grab ] Success-----',$url) | Out-File $logFile -Append
        return $true
    }
    Catch {
        -Join('[grab ] Failed------',$url) | Out-File $logFile -Append
        return $false
    }
    Finally {
    }
}


function Grab-Parse-List() {

    if (Test-Path -Path $listPagePath) {
    } else {
        New-Item -Path $listPagePath -ItemType directory
    }

    Get-Content $srcUrl | %{
        $tokens = $_.Split(' ',[StringSplitOptions]::RemoveEmptyEntries)
        $catalog = $tokens[0]
        $baseUrl = $tokens[1]

        
        [string]$catalogPath = -Join($basePath, $catalog)
        [string]$goodsFile = -Join($basePath, $catalog, '\', $goodsFileName)

        if (Test-Path -Path $catalogPath) {
        } else {
            New-Item -Path $basePath -Name $catalog -ItemType directory
        }

        if (Test-Path -Path $goodsFile) {
            Remove-Item $goodsFile
        }

        [int]$pageNo = 1
        [boolean]$continue = $true
        do{
            [string]$htmlFileName = $catalog + '-' + $pageNo + '.html'
            [string]$htmlFile = -Join($listPagePath,$htmlFileName)

            [string]$url = -Join($baseUrl, '?p=', $pageNo)

            if (Download-File -url $url -outFile $htmlFile) {
                $continue = Parse-List-Page -htmlFile $htmlFile -outFile $goodsFile
            }
            -Join('Current page: ', $pageNo, '; Has next page: ', $continue) | Write-Output 
            $pageNo = $pageNo + 1
        } while($continue)

    }
}


function Parse-List-Page {
    Param([Parameter(
        Mandatory = $true,
        ParameterSetName = '',
        ValueFromPipeline = $false)]
        [string]$htmlFile,
        [Parameter(
        Mandatory = $true,
        ParameterSetName = '',
        ValueFromPipeline = $false)]
        [string]$outFile
        )
    

    Try {
        $doc = New-Object HtmlAgilityPack.HtmlDocument
        $doc.LoadHtml((get-content $htmlFile -encoding utf8))
        
        
        
        $doc.DocumentNode.SelectNodes("//div[@class='product-image-top']/div/a") | ForEach-Object {
            -Join($_.Attributes['href'].value, '                 ',$_.SelectSingleNode("img").Attributes['src'].Value) | Out-File -Append $outFile
        }

        [boolean]$hasNext = $false
        if ($doc.DocumentNode.SelectSingleNode("//li/a[@class='next i-next']")) {
            $hasNext = $true
        }
        return $hasNext
    }
    Catch {
        -Join('[parse] Failed-----',$htmlFile) | Out-File $logFile -Append
        return $false
    }
    Finally {
    }
}


function Grab-Goods() {

    Get-Content $srcUrl | %{
        $tokens = $_.Split(' ',[StringSplitOptions]::RemoveEmptyEntries)
        [string]$catalog = $tokens[0]

        
        [string]$catalogPath = -Join($basePath, $catalog)
        [string]$goodsFile = -Join($basePath, $catalog, '\', $goodsFileName)

        if (Test-Path -Path $goodsFile) {
            [int]$goodNo = 1
            Get-Content $goodsFile | %{

                $tokens = $_.Split(' ',[StringSplitOptions]::RemoveEmptyEntries)
                [string]$goodUrl = $tokens[0]
                [string]$coverImgUrl = $tokens[1]

                [string]$oneGoodPath=-Join($catalogPath, '\', $goodNo)
                if (Test-Path -Path $oneGoodPath) {
                    Remove-Item $oneGoodPath -Recurse
                }
                New-Item -Path $oneGoodPath -ItemType directory

                [string]$goodHtml = -Join($oneGoodPath,'\',$goodNo,'.html')
                [string]$coverImg = -Join($oneGoodPath,'\', 'cover.jpg')

                Download-File -url $goodUrl -outFile $goodHtml
                Download-File -url $coverImgUrl -outFile $coverImg

                $goodNo = $goodNo + 1
            }
        }
    }
}


function Parse-One-Good-Page {
    Param([Parameter(
        Mandatory = $true,
        ParameterSetName = '',
        ValueFromPipeline = $false)]
        [string]$goodHtml,
        [Parameter(
        Mandatory = $true,
        ParameterSetName = '',
        ValueFromPipeline = $false)]
        [string]$titleFile,
        [Parameter(
        Mandatory = $true,
        ParameterSetName = '',
        ValueFromPipeline = $false)]
        [string]$imageFile,
        [Parameter(
        Mandatory = $true,
        ParameterSetName = '',
        ValueFromPipeline = $false)]
        [string]$detailImageFile
        )
    
    <#
    Write-Output $goodHtml
    Write-Output $titleFile
    Write-Output $imageFile
    #>
    Try {
        $doc = New-Object HtmlAgilityPack.HtmlDocument
        $doc.LoadHtml((get-content $goodHtml -encoding utf8))
        
        if (Test-Path -Path $titleFile) {
            Remove-Item $titleFile
        }

        if (Test-Path -Path $imageFile) {
            Remove-Item $imageFile
        }

        if (Test-Path -Path $detailImageFile) {
            Remove-Item $detailImageFile
        }
        
        $doc.DocumentNode.SelectSingleNode("//div[@class='product-view']//h1[@class='dp-product-name']").InnerText | Out-File $titleFile
        $doc.DocumentNode.SelectNodes("//div[@class='product-view']//div[@class='product-image product-image-zoom']//img") | ForEach-Object {
            $_.Attributes['src'].Value | Out-File -Append $imageFile
        }
        $doc.DocumentNode.SelectNodes("//div[@id='product_tabs_attributes_tabbed_contents']//img") | ForEach-Object {
            $_.Attributes['src'].Value | Out-File -Append $detailImageFile
        }
    <#
    Write-Output $doc.DocumentNode.SelectNodes("//div[@class='product-view']//div[@class='product-image product-image-zoom']//img")
        #>
    }
    Catch {
        -Join('[parse] Failed-----',$htmlFile) | Out-File $logFile -Append
        return $false
    }
    Finally {
    }
}


function Parse-All-Good-Pages() {

    Get-Content $srcUrl | %{
        $tokens = $_.Split(' ',[StringSplitOptions]::RemoveEmptyEntries)
        [string]$catalog = $tokens[0]

        [string]$catalogPath = -Join($basePath, $catalog)

        Get-ChildItem $catalogPath -Directory|%{
            $goodFolder = $_.Name
            $goodHtml = -Join($catalogPath, '\', $goodFolder, '\', $goodFolder, '.html')
            $titleFile = -Join($catalogPath, '\', $goodFolder, '\', $titleFileName)
            $imageFile = -Join($catalogPath, '\', $goodFolder, '\', $imageFileName)
            $detailImageFile = -Join($catalogPath, '\', $goodFolder, '\', $detailImageFileName)
            
            Parse-One-Good-Page -goodHtml $goodHtml -titleFile $titleFile -imageFile $imageFile -detailImageFile $detailImageFile
            <#
            Write-Output $goodHtml
            Write-Output $titleFile
            Write-Output $imageFile
            #>
        }

    }
}

function Grab-Product-Images() {

    Try {

        Get-Content $srcUrl | %{
            $tokens = $_.Split(' ',[StringSplitOptions]::RemoveEmptyEntries)
            [string]$catalog = $tokens[0]

            [string]$catalogPath = -Join($basePath, $catalog)

            Get-ChildItem $catalogPath -Directory|%{
                $goodFolder = $_.Name
                $titleFile = -Join($catalogPath, '\', $goodFolder, '\', $titleFileName)
                $imageFile = -Join($catalogPath, '\', $goodFolder, '\', $imageFileName)
                [int]$productImageIndex = 0
                #Write-Output $productImageIndex
                #Write-Output $imageFile
                Get-Content $imageFile | %{
                    [string]$piFileName = -Join('pi',$productImageIndex, '.jpg')
                    [string]$piFile = -Join($catalogPath, '\', $goodFolder, '\',$piFileName)
                    
                    if (Test-Path -Path $piFile) {
                        Remove-Item $piFile
                    }

                    Download-File -url $_ -outFile $piFile
                    $piFileName | Out-File -Append $titleFile
                    #Write-Output $piFile
                    $productImageIndex = $productImageIndex + 1
                }
            }

        }

    }
    Catch {
        -Join('[grab ] Failed------Grab all product images') | Out-File $logFile -Append
    }
    Finally {
    }
}


function Grab-Desciption-Images() {

    $descFile = -Join($desciptionFolder, '\', $desciptionFileName)
    Try {
    
        if (Test-Path -Path $desciptionFolder) {
            Remove-Item $desciptionFolder -Recurse
        }
        New-Item -Path $desciptionFolder -ItemType directory

        Get-Content $srcUrl | %{
            $tokens = $_.Split(' ',[StringSplitOptions]::RemoveEmptyEntries)
            [string]$catalog = $tokens[0]
            [string]$catalogPath = -Join($basePath, $catalog)

            Get-ChildItem $catalogPath -Directory|%{
                $goodFolder = $_.Name
                $titleFile = -Join($catalogPath, '\', $goodFolder, '\', $titleFileName)
                $detailImageFile = -Join($catalogPath, '\', $goodFolder, '\', $detailImageFileName)
            
    
                if (Test-Path -Path $detailImageFile) {

                    Get-Content $detailImageFile | %{
                        $tokens = $_.Split('/')
                        $descImgFileName=$tokens[-1]
                        $isSubFolder = $false;
                        $subFolder = ''
                        for ($i=0; $i -lt $tokens.length-1; $i++){
                            if ($isSubFolder) {
                                $subFolder = -Join($subFolder,'\',$tokens[$i])
                            }
                            if ($tokens[$i] -eq 'products_description') {
                                $isSubFolder = $true
                            }
                        }
                        -Join($subFolder, '\', $descImgFileName) | Out-File -Append $titleFile

                        $descFolder = -Join($desciptionFolder, $subFolder)
                        
                        if (Test-Path -Path $descFolder) {
                        } else {
                            New-Item -Path $descFolder -ItemType directory
                        }

                        $descImgFile = -Join($descFolder, '\', $descImgFileName)
                        if (Test-Path -Path $descImgFile) {
                        } else {
                            Download-File -url $_ -outFile $descImgFile
                        }
                    }

                }
            }

        }

    }
    Catch {
        -Join('[grab ] Failed------Grab all product description images') | Out-File $logFile -Append
    }
    Finally {
    }
}




function Main-Entry() {    
    # 分步执行
    # 1、抓取列表页
    Grab-Parse-List

    # 2、抓取商品页
    Grab-Goods

    # 3、解析商品页
    Parse-All-Good-Pages
    
    # 4、抓取商品图片
    Grab-Product-Images
    
    # 5、抓取商品描述图片
    Grab-Desciption-Images
}



# Main-Entry