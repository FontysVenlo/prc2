# PRC2 Documentation

This repository houses the documentation for the course PRC2 at Fontys Venlo.  

The documentation will be automatically converted to be shown as Github pages at [https://fontysvenlo.github.io/prc2/](https://fontysvenlo.github.io/prc2/)

## Building

Prerequisites are:

- [Hugo](https://gohugo.io/) extended edition (for `SASS` build)
- [npm](https://nodejs.org/en/) for installing `Javascript` packages
- [Asciidoctor](https://asciidoctor.org/) for converting `AsciiDoc`
- [asciidoctor-html5s](https://github.com/jirutka/asciidoctor-html5s) for converting to semantic html5

To build:

1. First install needed `Javascript` packages with the following command: `npm install`
2. Build the website using `Hugo`
    - Build production: `hugo --minify`
    - Build development: `hugo server`
    - Build development with drafts: `hugo server -D`

## Front matter

Every page (in content) has what is called a [front matter](https://gohugo.io/content-management/front-matter/). Here we can specify certain variables. 

The most important variables in the front matter are

- **title**: *string*, The title of the page
- **draft**: *boolean*, whether the page should show up in production build 
- **slug**: *string*, what the URL to this page should look like, if not specified will be the file name
- menu: specifies that it should be part of a menu
    - menu-name: *string*, the menu name
        - weight: *int*, the weight of the menu item, lower weight if lower in the menu

## Structure

### Content

The content directory houses the pages that should be generated from the documentation.

There are thee different kinds of pages.

#### Homepage

The homepage can be found in `_index.adoc`.

#### Docs

These pages contain the documentation for every week of the course.  
These pages show up in the sidebar menu.
To create a new docs page use the following command: `hugo new docs/<week##>`

> :information_source: **The convention is to call the files week##**

#### Pages

These pages contain miscellaneous content.  
These pages show up in the header menu.
To create a new page use the following command: `hugo new pages/<name>`

### Static

This directory contains all the static files, namely
1. icons
2. images
3. webfonts

### Theme

This is a submodule that contains the theme that is used for the layout of the pages. The theme used is the [sebi-theme](https://github.com/FontysVenlo/sebi-theme).

### Topics

This directory contains all the includes that are referenced by `docs` and `pages`.

### config.yaml

This file contains the configuration for `Hugo`, `asciidoctor` and `sebi-theme`.

### Package.json

This file contains the needed packages to be able to build the website.