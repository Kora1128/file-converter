# 📁 File Converter

A Kotlin-based command-line application designed to convert files between various formats seamlessly.

## 🚀 Features

- **Multi-format Conversion**: Convert files between different formats (e.g., `.txt` to `.pdf`, `.docx` to `.txt`).
- **Batch Processing**: Handle multiple files in a single command.
- **Customizable Output**: Specify output directories and file naming conventions.
- **Extensible Architecture**: Easily add support for new file formats.

## Build the Project

`./gradlew build`

## 🔧 Dependencies

This project uses the following libraries:

- [`iText 7 Core`](https://github.com/itext/itext7) – for creating and manipulating PDFs.
- [`Apache PDFBox`](https://pdfbox.apache.org/) – for extracting content from PDFs and other operations.
