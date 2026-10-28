package ru.itis.cli;

import java.io.PrintStream;
import ru.itis.cli.command.Command;
import ru.itis.cli.command.ListAlbums;
import ru.itis.cli.command.ListPhotos;
import ru.itis.cli.command.Upload;
import ru.itis.cli.command.Download;
import ru.itis.cli.command.DeleteAlbum;
import ru.itis.cli.command.DeletePhoto;
import ru.itis.cli.command.MakeSite;
import ru.itis.site.SitePublisher;
import ru.itis.service.PhotoArchiveService;

public final class CommandExecutor {
    private final PhotoArchiveService service;
    private final PrintStream output;
    private final SitePublisher sitePublisher;

    public CommandExecutor(PhotoArchiveService service, SitePublisher sitePublisher, PrintStream output) {
        this.service = service;
        this.sitePublisher = sitePublisher;
        this.output = output;
    }

    public void execute(Command command) {
        switch (command) {
            case ListAlbums ignored -> service.listAlbums().forEach(output::println);
            case ListPhotos photos -> service.listPhotos(photos.album()).forEach(output::println);
            case Upload upload -> service.upload(upload.album(), upload.path());
            case Download download -> service.download(download.album(), download.path());
            case DeleteAlbum album -> service.deleteAlbum(album.album());
            case DeletePhoto photo -> service.deletePhoto(photo.album(), photo.photo());
            case MakeSite ignored -> sitePublisher.publish();
        }
    }
}
