package org.waltz4line.rocksdb;

import org.rocksdb.RocksDB;
import org.rocksdb.SstFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waltz4line.rocksdb.move.SstFileMover;
import org.waltz4line.rocksdb.util.FileUtil;
import org.waltz4line.rocksdb.util.RocksdbUtil;
import org.waltz4line.rocksdb.util.SstFileReaderUtils;

import java.util.List;

public class CopySstToNewRocksdb {

    private static final Logger logger = LoggerFactory.getLogger(CopySstToNewRocksdb.class);

    private static final byte PREFIX = 0x02;

    public static void main(String[] args) throws Exception {
        String srcDirectory = "./dummy";
        String targetDirectory = "./target-db";

        if (args.length == 2) {
            srcDirectory = args[0];
            targetDirectory = args[1];
        }

        List<String> sstFiles = FileUtil.getSstFiles(srcDirectory);
        logger.info("*.sst files : {}", sstFiles);
        try (RocksDB rocksDB = RocksdbUtil.openRocksdb(targetDirectory)) {
            SstFileMover sstFileMover = new SstFileMover(PREFIX, rocksDB);
            for (String sstFile : sstFiles) {
                logger.info("### sst files read started. filename={}", sstFile);
                try (SstFileReader reader = SstFileReaderUtils.open(sstFile)) {
                    sstFileMover.copy(reader);
                }
                logger.info("### sst files read finished. filename={}", sstFile);
            }
        }
    }

}
