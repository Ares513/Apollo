package com.team1ofus.apollo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DataManagement implements IUIManagementInteractionListener {
	ArrayList<Cell> cells;
	public DataManagement() {
		cells = new ArrayList<Cell>();
		//process: load blank map into memory, then save it

		try {
			
			cells = loadAllCells();
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//make a placeholder map.
		if(cells.size() == 0) {
			cells.add(makePlaceholderMap());
		}
		
	}
	/*
	 * Create a temporary map.
	 */
	private Cell makePlaceholderMap() {
		Cell output;
		return output = new Cell(50, 50, 1, TILE_TYPE.WALL);
		
	}
	private ArrayList<Cell> loadAllCells() throws ClassNotFoundException, IOException {
		ArrayList<Cell> output = new ArrayList<Cell>();
			List<Path> filteredPaths = getAvailableCells(BootstrapperConstants.APP_FILE_DIRECTORY);
			for(Path p : filteredPaths) {
				output.add(loadCell(p.toString()));
					
			}
			if(output.size() == 0) {
				//there are no maps that can be loaded.
				output.add(makePlaceholderMap());
			}
			return output;	
			
		
		
	}
	private void saveAllCells(ArrayList<Cell> cellsToSave) throws ClassNotFoundException, IOException {
		for(Cell c : cellsToSave) {
			
				saveCell(BootstrapperConstants.APP_FILE_DIRECTORY + "\\" + c.getID() + ".map", c);

		}
	}
	private Cell loadCell(String pathWithNameAndExtension) throws IOException, ClassNotFoundException {
			
			FileInputStream in = new FileInputStream(pathWithNameAndExtension);
			
			ObjectInputStream objIn = new ObjectInputStream(in);
			Cell result = (Cell) objIn.readObject();
			objIn.close();
			in.close();
			return result;
			
			//The default.map file isn't located.
			// TODO Auto-generated catch block		
	}
	private void saveCell(String pathWithNameAndExtension, Cell cellToWrite) throws IOException, ClassNotFoundException {
		FileOutputStream out = new FileOutputStream(pathWithNameAndExtension);
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		
		objOut.writeObject(cellToWrite);
		objOut.close();
	}
	private List<Path> getAvailableCells(String directoryPathWithNameAndExtension) throws IOException {
		List<Path> output = new ArrayList<Path>();
		File file = new File(BootstrapperConstants.APP_FILE_DIRECTORY);
		
		File[] listOfFiles = file.listFiles();
		for(File f : listOfFiles) {
			if(f.isFile()) {
				String fileName = f.getName();
				if(fileName.contains(".map")) {
					output.add(f.toPath());
				}
			}
		}
		return output;
		/*
		 * Deprecated due to stream problems 11/17/2015;
//		 */
//		Stream<Path> paths = Files.walk(Paths.get(BootstrapperConstants.APP_FILE_DIRECTORY));
//		Stream<Path> filtered = paths.filter(isCell());
//		paths.close();
//		//now we know the ones that are actually maps
//		return filtered.collect(Collectors.toList());
		
	}
	public static Predicate<Path> isCell() {
		return p -> p.getFileName().toString().contains(".map");
	}
	
	public ArrayList<Cell> getCells() {
		return cells;
	}
	
	@Override
	public void onSaveTriggered(ArrayList<Cell> cellsToSave) {
		try {
			saveAllCells(cellsToSave);
		} catch (ClassNotFoundException e) {
			//Something is wrong with our serialization format.
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			//Resource or permissions error.
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private List<Cell> streamToList(Stream<Cell> input) {
		return input.collect(Collectors.toList());
		
	}
}