package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    // the most recent block
    List<Block> chain;
    Integer size;

    public Blockchain(){
        this.chain = new ArrayList<>();
        this.size = 0;
    }
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void add(Block block) {
        chain.add(block);
        size += 1;
    }

    public int size() {
        return this.size;
    }

    public boolean isValid() throws NoSuchAlgorithmException {

        // todo - check an empty chain

        // todo - check a chain of one

        // todo - check a chain of many
        if (this.size == 0) {
            return true;
        } else if (this.size == 1) {
            if (this.chain.get(0).getPreviousHash() != "0" || !isMined(this.chain.get(0)) || this.chain.get(0).getHash().length() < 60) {
                return false;
            }
        } else {
            for (int i = 1; i < this.size; i++) {
                if (this.chain.get(i).getPreviousHash() != this.chain.get(i - 1).getHash() || !isMined(this.chain.get(i))  || this.chain.get(0).getHash().length() < 60){
                    return false;
                }
            }
        }

        return true;
    }

    /// Supporting functions that you'll need.

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }
        return mined;
    }

    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
//        return true;
    }

}