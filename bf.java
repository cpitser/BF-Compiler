// INSTRUCTIONS
// > move right
// < move left
// + increment
// - decrement
// . output
// , input and store
// [ loop start
// ] loop end

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Stack;

public class bf {

    private static final int TAPESIZE = 100;
	private static int tapeIndex;
	private static int[] tape;
	private static int lineCounter;
	public static int i;
	public static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Incorrect Usage\nUsage: bf [file]");
		} else {
			File file = new File(args[0]);
			try {
				Scanner fileScanner = new Scanner(file);
				bf.tape = new int[TAPESIZE];
				bf.i = 0;
				while (bf.i < TAPESIZE) {
					bf.tape[bf.i] = 0;
					bf.i++;
				} // for
				bf.tapeIndex = 0;
				bf.lineCounter = 0;
				while (fileScanner.hasNextLine()) {
					bf.lineCounter++;
					String line = fileScanner.nextLine();
					bf.i = 0;
					while (bf.i<line.length()) {
						bf.evaluate(line, bf.i);
						bf.i++;
					} // for
				} // while
				System.out.println();
			} catch (FileNotFoundException fnfe) {
				System.err.println("Input file was not found.");
				System.exit(1);
			} catch (IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
				System.exit(1);
			} // try
		} // if
    } // main

	private static void evaluate(String line, int x) throws IllegalArgumentException {
		switch (line.charAt(x)) {

			case '>':
				bf.tapeIndex++;
				break;

			case '<':
				if (bf.tapeIndex != 0) { bf.tapeIndex--; }
				break;

			case '+':
				bf.tape[bf.tapeIndex]++;
				break;

			case '-':
				bf.tape[bf.tapeIndex]--;
				break;

			case '.':
				System.out.print((char)bf.tape[bf.tapeIndex]);
				break;

			case ',':
				bf.tape[bf.tapeIndex] = userInput.nextInt();
				break;

			case '[':
				bf.recursion(line.substring(i+1,line.length()));
				break;

			case ']':
				throw new IllegalArgumentException("Invalid loop syntax at line: " + bf.lineCounter);

		} // switch
	} // evaluate

	private static void recursion(String line) throws IllegalArgumentException {
		boolean quit = false;
		int lineIndex = 0;
		while (!quit) {
			while (lineIndex != line.length() && line.charAt(lineIndex) != ']') {								
				bf.evaluate(line, lineIndex);
				lineIndex++;
			} // while
			if (lineIndex == line.length()) {
				throw new IllegalArgumentException("Incorrect loop syntax at line: " + bf.lineCounter);
			} else {
				if (bf.tape[bf.tapeIndex] != 0) {
					lineIndex = 0;
				} else {
					quit = true;
					bf.i += lineIndex + 1;
				} // if
			} // if
		} // while
	} // recursion

} // bf
