package ru.itis.cli.command;

public sealed interface Command
        permits ListAlbums, ListPhotos, Upload, Download, DeleteAlbum, DeletePhoto, MakeSite {
}
