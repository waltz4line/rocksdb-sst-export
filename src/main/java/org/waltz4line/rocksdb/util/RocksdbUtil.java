package org.waltz4line.rocksdb.util;

import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocksdbUtil {

    private static final Logger logger = LoggerFactory.getLogger(RocksdbUtil.class);

    public static RocksDB openRocksdb(String path) throws RocksDBException {
        return RocksDB.open(newOptions(), path);
    }

    public static void printKeys(RocksDB rocksDB) {
        int readCount = 0;
        logger.info("==================== start print keys =====================");
        try(ReadOptions readOptions = new ReadOptions()) {
            try(RocksIterator rocksIterator = rocksDB.newIterator(readOptions)) {
                rocksIterator.seekToFirst();
                while (rocksIterator.isValid()) {
                    readCount++;
                    logger.info(HexUtil.toHexString(rocksIterator.key()));
                    rocksIterator.next();
                }
            }
        }
        logger.info("Read count:{}", readCount);
        logger.info("==================== finish print keys =====================");
    }

    public static Options newOptions() {
        Options options = new Options();
        options.setCreateIfMissing(true);
        options.setAllowConcurrentMemtableWrite(true);
        options.setCompressionType(CompressionType.SNAPPY_COMPRESSION);
        options.setKeepLogFileNum(5);
        options.setWriteBufferSize(4 * 1024 * 1024);
        return options;
    }

}
