## Nedir?
[English](https://github.com/KaplanBedwars/GitDW_/blob/main/readmee.md)

XML Dosyası ile link'ten dosya çekme.


## Kullanım

Çok basittir. [İndirin](https://github.com/KaplanBedwars/GitDW_/releases/tag/v.1.0.1ALPHA) Ardından ayıklayıp **JAR** dosyasını açın.

Aşşağıdaki komutu yazın:

```cmd
open -xml 
```
Ardından açılan dosya şöyle olmalıdır:

```xml
<config>
    <project>
        <name>MyJavaProject</name>
        <version>1.0</version>
        <githubLinks>
            <link></link>
            
        </githubLinks>
    </project>
</config>

```
Bu iki etiketin arasında linkinizin olması gerekir.:
```xml
<link></link> 
```
  Birden fazla link olabilir:
  ```xml
      <link>https://teamcord-33be0.web.app/a/index.html</link>
            <link>https://sites.google.com/view/kaplanbedwars</link>

```

Bu linklerdeki dosyalar indirileckektir.
## Bağımlılıklar

Bu proje **Pom.xml** dosyasında olmayan bir bağımlılık ister:
>Eğer projeyi kopyaladıysanız bunu ekleyin:

[ErrorLOG](https://github.com/KaplanBedwars/Better-error-message)

  
## Lisans

[MIT](https://choosealicense.com/licenses/mit/)

  
