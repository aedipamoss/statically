# statically

Static site generator

This program takes a directory of markdown files and converts them to HTML while embedding the result into a common template.

```
java -jar path/to/uberjar/static-0.1.0-SNAPSHOT-standalone.jar
```

You must have the following in the current working directory:

* "blog": source directory for markdown files
* "output": directory where resulting HTML is generated
* "templates/template.html": HTML template file where content is embedded

Here is a very basic example of a `template.html` file:

```html
<html>
  <head>
  <title>Your Page Title Here</title>
  </head>
  <body>
    {{content|safe}}
  </body>
</html>
```

Here are some ideas to expand this program

* Cleanup readme, changelog, docs, etc
* Add option parsing to select source, output, and templates
* Templates in markup besides HTML

## License

Copyright © 2017 Ædipa Moss

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
