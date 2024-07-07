package org.waltz4line.rocksdb.move;

import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waltz4line.rocksdb.util.EndianUtil;
import org.waltz4line.rocksdb.util.HexUtil;

import java.util.concurrent.atomic.AtomicLong;

public class SstFileMover {

    private static final Logger logger = LoggerFactory.getLogger(SstFileMover.class);

    private final AtomicLong sequence = new AtomicLong();

    private final byte prefix;

    private final RocksDB targetRocksDB;

    public SstFileMover(byte prefix, RocksDB targetRocksDB) {
        this.prefix = prefix;
        this.targetRocksDB = targetRocksDB;
    }

    public void copy(SstFileReader sstFileReader) {
        long previousKey = seekLastSequence();
        logger.info("Previous last key: {}", previousKey);
        sequence.set(previousKey + 1);
        try (ReadOptions readOptions = new ReadOptions()) {
            try (SstFileReaderIterator iterator = sstFileReader.newIterator(readOptions)) {
                iterator.seekToFirst();
                while (iterator.isValid()) {
                    byte[] value = iterator.value();
                    byte[] key = new byte[7];
                    key[0] = prefix;
                    EndianUtil.partialLongToBytes(key, sequence.getAndIncrement(), 1, 7);
                    logger.debug("key move {} to {}." ,HexUtil.toHexString(iterator.key()), HexUtil.toHexString(key));
                    targetRocksDB.put(key, value);
                    iterator.next();
                }
            }
            logger.info("Added last key: {}, Added record count:{}.", sequence.get() - 1 , sequence.get() - previousKey - 1);
        } catch (Exception e) {
            logger.error("Failed to copy..", e);
        }
    }

    public long seekLastSequence() {
        try (ReadOptions readOptions = new ReadOptions()) {
            try (RocksIterator iterator = targetRocksDB.newIterator(readOptions)) {
                iterator.seekToLast();
                if (iterator.isValid()) {
                    return EndianUtil.toInt64(iterator.key(), 1, 6);
                }
            }
        }
        return 0;
    }

}
