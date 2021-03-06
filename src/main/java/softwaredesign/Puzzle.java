package softwaredesign;

import java.io.*;
import java.util.Iterator;

public class Puzzle implements Iterable<String[]> {
    private Matrix matrix;
    private Sequences sequences;
    private String currPuzzle;
    private int bufferLen;
    private ContainsQueue cQueue;

    public Puzzle() {
        cQueue = new ContainsQueue();
    }

    public void getNextPuzzle() {
        this.currPuzzle = getPuzzleContent();
        String[] puzzleParts = currPuzzle.split("\n\n");

        //puzzleParts[0] is buffer length
        try {
            this.bufferLen = Integer.parseInt(puzzleParts[0]);
        } catch (NumberFormatException e) {
            throw new RuntimeException("This is not a valid format for buffer size.");
        }

        //puzzleParts[1] is the matrix
        String matrixTxt = puzzleParts[1];
        this.matrix = new Matrix(matrixTxt);

        //puzzleParts[2] are the sequences
        String seqTxt = puzzleParts[2];
        this.sequences = new Sequences(seqTxt);
    }

    private int getRandomNumber(int min, int max){
        return (int)(Math.random() * (max - min + 1) + min);
    }

    private String getPuzzleContent() {
        final int MIN_FILE_NUM = 1;
        final int MAX_FILE_NUM = 40;
        FileReader fr = new FileReader();

        //make sure file numbers don't repeat to often
        int fileNumber = getRandomNumber(MIN_FILE_NUM, MAX_FILE_NUM);
        while (cQueue.contains(fileNumber)) {
            fileNumber = getRandomNumber(MIN_FILE_NUM, MAX_FILE_NUM);
        }
        cQueue.enQueue(fileNumber); //save current file number

        String fileName = "puzzles/" + fileNumber + ".txt";
        String file = "";
        try {
            file = fr.readFile(fileName);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return file;
    }

    public int getBufferLen(){
        return this.bufferLen;
    }

    public int getCurrNumOfSeq() {
        return this.sequences.getNumOfSeq();
    }

    public String getCurrMatrixElement(int row, int col) {
        return this.matrix.getMatrixElement(row, col);
    }

    public int getCurrMatrixDims() {
        return this.matrix.getMatrixDims();
    }

    @Override
    public Iterator<String[]> iterator() {
        return this.sequences;
    }
}
