package acme.me.j2se.io;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileSystemMonitor {
    @Test
    public void test1() throws Exception {
        FileSystem fileSystem = FileSystems.getDefault();
        WatchService watchService = fileSystem.newWatchService();
        Path path = Paths.get("pathToDir");
        WatchKey watchKey = path.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
            }
            key.reset();
        }
    }

    @Test
    public void test2() throws IOException {
        Path startingDir = Paths.get("pathToDir");
        FileVisitorImpl visitor = new FileVisitorImpl();
        Files.walkFileTree(startingDir, visitor);
    }

    @Test
    public void test3() throws IOException {
        Path startPath = Paths.get("\\CallGuidesTXT\\");
        Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                String firstLine = Files.newBufferedReader(file, Charset.defaultCharset()).readLine();
                System.out.println(firstLine);
                return FileVisitResult.CONTINUE;
            }
        });
    }


    public class FileVisitorImpl implements FileVisitor<Path> {
        @Override
        public FileVisitResult preVisitDirectory(
                Path dir, BasicFileAttributes attrs) {
            return null;
        }

        @Override
        public FileVisitResult visitFile(
                Path file, BasicFileAttributes attrs) {
            return null;
        }

        @Override
        public FileVisitResult visitFileFailed(
                Path file, IOException exc) {
            return null;
        }

        @Override
        public FileVisitResult postVisitDirectory(
                Path dir, IOException exc) {
            return null;
        }
    }
}
