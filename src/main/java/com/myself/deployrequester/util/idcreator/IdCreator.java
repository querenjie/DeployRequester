package com.myself.deployrequester.util.idcreator;

/**
 * Created by QueRenJie on ${date}
 */
public class IdCreator {
    private static IdWorker idWorker = null;

    public IdCreator() {
    }

    public static long getNextId() {
        return idWorker.nextId();
    }

    static {
        idWorker = new IdWorker();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++)
        System.out.println(IdCreator.getNextId());
    }
}
