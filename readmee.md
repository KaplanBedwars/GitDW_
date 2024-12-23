 ## What is?

Extract file from link with XML File.


## Usage

It is very simple. [Download](https://github.com/KaplanBedwars/GitDW_/releases/tag/v.1.0.1ALPHA) Then extract and open the **JAR** file.

Type the following command:

```cmd
open -xml 
```
Then the file opened should be like this:

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
You must have a link between these two tags:
```xml
<link></link> 
```
  There may be more than one link:
  ```xml
      <link>https://teamcord-33be0.web.app/a/index.html</link>
            <link>https://sites.google.com/view/kaplanbedwars</link>

```

The files in these links will be downloaded.
## Dependencies

This project requires a dependency that is not in **Pom.xml**:
>Add this if you copied the project:

[ErrorLOG](https://github.com/KaplanBedwars/Better-error-message)

  
## Licence

[MIT](https://choosealicense.com/licenses/mit/)

  

