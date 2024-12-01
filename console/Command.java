package console;

import code_clone.CloneCheck;
import huffman.mainDecode;
import huffman.mainEncode;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import metrices.Average_LOC;
import metrices.FileCount;
import metrices.LineOfCode;
import metrices.MethodCount;
import searching.Search;

public class Command {

    public static String forwardDir;
    String directoryName = null;
    public static String currentPath = null;
    //  boolean pexist = false;
    //String project;

    Scanner scan = new Scanner(System.in);

    public void command() throws IOException {

        String projectSelect = null;
        while (true) {
            if (currentPath != null) {
                System.out.print("" + currentPath + ">");
            }
            if (currentPath == null) {
                currentPath = getcurrentPath();
                System.out.print("" + currentPath + ">");
            }

            String choice = scan.nextLine().trim();
            Pattern forward = Pattern.compile("(?i)\\b(cd)\\b\\s+(.+)");

            Pattern specialChar = Pattern.compile("[\"*<>\\/://?\\|\\.]+");
            Matcher m = forward.matcher(choice);

            Matcher special = specialChar.matcher(choice);
            boolean specialvalue = special.find();

            boolean wardval = m.find();

            private void handleCommand(String choice) throws IOException {
                switch (choice.toLowerCase()) {
                    case "help":
                        displayHelp();
                        break;
                    case "1":
                    case "clone":
                        handleClone();
                        break;
                    case "2":
                    case "file_compress & file_decompress":
                        handleFileCompressDecompress();
                        break;
                    case "fcom":
                        new mainEncode().Compress(currentPath);
                        break;
                    case "dcom":
                        new mainDecode().Decompress(currentPath);
                        break;
                    case "3":
                    case "search":
                        handleSearch();
                        break;
                    case "4":
                    case "metrics":
                        handleMetrics();
                        break;
                    case "mc":
                    case "method_count":
                        getMethodCount(currentPath);
                        break;
                    case "loc":
                    case "line_of_code":
                        getLineOfCode(currentPath);
                        break;
                    case "a_loc":
                        getAverageLOC(currentPath);
                        break;
                    case "fc":
                    case "file_count":
                        getTotalClassCount(currentPath);
                        break;
                    case "exit":
                    case "5":
                        System.exit(0);
                        break;
                    default:
                        handlePathNavigation(choice);
                        break;
                }
            }        
    }
}

    public String getcurrentPath() {
        String path;

        if (currentPath == null) {
            path = FileSystems.getDefault().getPath("").toAbsolutePath().toString();

        } else {
            path = currentPath;
        }

        return path;

    }

    public void getMethod(String path) throws IOException {      //Count total number of methods of a project/java file
        
        String newpath = pathGenerate(path);
        try {
            System.out.print("\tProject\\File Name:");
            String name = Input();
            String projectPath = newpath + "\\" + name;
            Path filepath = Paths.get(projectPath);
            if (Files.exists(filepath) && Files.isDirectory(filepath) && !name.isEmpty()) {
              //  System.out.println("project");
             new MethodCount().getTotalMethods(projectPath, name);
            } else if (Files.exists(filepath) && !Files.isDirectory(filepath) && !name.isEmpty()) {
                String file = filepath.toString();
                new MethodCount().getTotalMethods(projectPath, name);

            } else {

                System.out.println("The program cannot find '" + name + "'");
            }
        } catch (Exception e) {
            System.out.println("Invalid input");

        }

    }

    public void LineCode(String Currentpath) throws IOException {
        String newpath = pathGenerate(Currentpath);    //count total number of line of a java file
        System.out.print("\tWrite the file name:");
       String fileName=Input();
        String p = newpath + "\\" + fileName.trim();
        try {
            Path path = Paths.get(p);
            if (Files.exists(path) && !Files.isDirectory(path)) {
                int totalLine = new LineOfCode().countLines(path.toString());
               System.out.println("\tLine of " + fileName + " is " + totalLine);
            } else {
                System.out.println("The program cannot find '" + fileName + "'");
            }
        } catch (Exception e) {
            System.out.println("Invalid filename");
        }

    }
  

    public String Input() {
        Scanner scan = new Scanner(System.in);
        String projectName = scan.nextLine().trim();
        return projectName;

    }

    public void getTotalClass(String currenctpath) {    //Count total number of class of a projct
        String newpath = pathGenerate(currenctpath);
        System.out.print("\tWrite Project name:");
        String projectName = Input();
        String path = newpath + "\\" + projectName;
        try {
            Path p = Paths.get(path);
            if (Files.exists(p) && Files.isDirectory(p) && !projectName.isEmpty()) {
             new FileCount().classCount(path);

            } else if (Files.exists(p) && !Files.isDirectory(p)) {
                System.out.println("Invalid project name");
            } else {
                System.out.println("The program cannot find '" + projectName + "'");
            }
        } catch (Exception e) {
            System.out.println("Invalid project name");
        }

    }

    public void average_Line_of_Project(String currentpath) { //average line of code of a class
        String newpath = pathGenerate(currentpath);        
        System.out.print("\tWrite the project name:");
        String projectName = Input();
        String path = newpath + "\\" + projectName;

        try {
            Path p = Paths.get(path);        
            if (Files.exists(p) && Files.isDirectory(p) && !projectName.isEmpty()) {
                new Average_LOC().totalClass(path);

            } else if (Files.exists(p) && !Files.isDirectory(p)) {
                System.out.println("Invalid project name");
            } else {
                System.out.println("The program cannot find '" + projectName + "'");
            }
        } catch (Exception e) {
            System.out.println("Invalid projectname");
        }

    }

    public void Search(String path) {
        String newpath = pathGenerate(path);
        String queryWithFile = scan.nextLine().trim();
        try {
            String query = queryWithFile.substring(queryWithFile.indexOf("\"") + 1, queryWithFile.lastIndexOf("\"")).trim();
            String projectName = queryWithFile.substring(queryWithFile.lastIndexOf("\"") + 1).trim();
            String p = newpath + "\\" + projectName;
            // System.out.println(p);
            Path filepath = Paths.get(p);
            if ((projectName.isEmpty() && !query.isEmpty()) || (query.isEmpty() && !projectName.isEmpty())) {
                System.out.println("Wrong Command");
            } else if (query.isEmpty() && projectName.isEmpty()) {
                System.out.println("Wrong Command");
            } 
            else{
              if(Files.exists(filepath)){
                 SearchResult(newpath, projectName, query);
              
              }else {
                    System.out.println("The program cannot find '" + projectName + "'");
                }
            
            }
        /*    else {
                if (Files.exists(filepath) && Files.isDirectory(filepath)) {
                    SearchResult(newpath, projectName, query);
                } else if (Files.exists(filepath) && !Files.isDirectory(filepath)) {
                    SearchResult(newpath, projectName, query);
                } 
                else {
                    System.out.println("The program cannot find '" + projectName + "'");
                }
            }*/

        } catch (Exception e) {
            
          System.out.println("Wrong Command");
        }//catch (Exception e) {
        //   e.printStackTrace();
        // }

    }

    public void SearchResult(String path, String projectName, String query) throws IOException {
        String projectPath = path + "\\" + projectName;
        new Search().processProject(projectPath, projectName);
        new Search().SearchingResult(query, projectPath);  //calculate similarity

    }

    public String pathGenerate(String path) {
        // System.out.println("p="+path);          //remove the last backslash from a path 
        String newPath = path;
        if (path.endsWith("\\")) {
            newPath = path.substring(0, path.length() - 1);
            //  System.out.println("new=" + newPath);
        }
        return newPath;

    }

    public void projectPath() throws IOException {         
        ArrayList<String> projectList = new ArrayList<>(2);
        System.out.print("\t\tFirst Project:");
       try{
        String Firstproject =Input();
            //  String FirstprojectPath = currentPath + "\\" + Firstproject;
          //   File project1=new File(FirstprojectPath);
        if (Firstproject.length() == 0 | Firstproject.contains(".")) {
            System.out.println("\tInvalid project name");
            command();
        }
   
        projectExist(Firstproject);
        System.out.print("\t\tSecond Project:");

        String SecondProject = Input();
      //   String SecondprojectPath = currentPath + "\\" + SecondProject;
            // File project2=new File(SecondprojectPath);
        if (SecondProject.length() == 0 | SecondProject.contains(".")) {
            System.out.println("\tInvalid project name");
            command();
        }
           
             
        projectExist(SecondProject);
       
        
        if (projectExist(Firstproject) && projectExist(SecondProject) && (!(Firstproject.isEmpty() | SecondProject.isEmpty()))) {

            projectList.add(Firstproject);
            projectList.add(SecondProject);
            CloneCheck ob1 = new CloneCheck();

            ob1.Code_clone(Firstproject, SecondProject);
            projectList.clear();
        }  else {

            System.out.println("Wrong command");
        }}catch(Exception e){
        
        }

    }

    public boolean projectExist(String projectName) throws IOException {
        boolean exist = false;
        String projectPath = currentPath + "\\" + projectName;
   
        
        try {
            Path path = Paths.get(projectPath);
          
            if (Files.exists(path)) {
               exist = true;
            }      
            else {
                System.out.println("The program cannot find project '" + projectName + "'");
                //   exist=false;
                command();

            }
        } catch (Exception e) {
            System.out.println("Invalid Input");
            command();
        }
        return exist;
    }

    public String backDirectory(String newpath) {
        String result = null;
        if (newpath.length() > 3) {    //show stringIndexoutofboundException
            result = newpath.substring(0, newpath.lastIndexOf("\\") + 1);
            if (result.endsWith("\\") && result.length() > 3) {
                result = result.substring(0, result.length() - 1);
            }
            currentPath = result;
            forwardDir = result;
        } else {
            currentPath = newpath;
            forwardDir = currentPath;

        }
        return currentPath;
    }

    public void forwardDirectory(String dirName) {
        String forwardPath = null;

        String result = null;
        if (currentPath == null) {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            forwardPath = path.toString().concat("\\" + dirName);
            checkFileExist(forwardPath);

        } else {
            forwardPath = currentPath + "\\" + dirName;

            checkFileExist(forwardPath);

        }

    }

    public void checkFileExist(String path) {
        Path p1 = Paths.get(path);
        try {
            if (Files.exists(p1)) {
                forwardDir = p1.toString();
                currentPath = p1.toString();
                currentPath = setDirectory(forwardDir).toString();
                if (currentPath.length() < 3) {
                    currentPath = setDirectory(forwardDir).toString() + "\\";
                } else {
                    currentPath = setDirectory(forwardDir).toString();
                }
                // System.out.println(currentPath);
                forwardDir = currentPath;

            } else {
                System.out.println("The program cannot find the path specified.");

            }
        } catch (Exception e) {
            System.out.println("Invalid path");
        }

    }

    public File setDirectory(String s) {
        File file = new File("");
        System.setProperty("user.dir", "\\" + s);
        // System.out.println("" + file.getAbsolutePath());

        return file.getAbsoluteFile();
    }

    public void listDirectory(String path) throws IOException {
        File f = new File(path);
        Path file = Paths.get(path);
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
        int dircount = 0;
        int filecount = 0;
        File[] flist = f.listFiles();
        if (flist.length != 0) {
            for (File filee : flist) {
                if (filee.isDirectory()) {
                    System.out.println(attr.creationTime() + "\t <DIR>\t\t" + filee.getName());
                    dircount++;
                } else {
                    System.out.println(attr.creationTime() + "\t \t\t" + filee.getName());
                    filecount++;
                }
            }
            System.out.println("\t" + dircount + " Dir(s)");
            System.out.println("\t" + filecount + " File(s)");
        } else {
            System.out.print("Empty directory");
        }
    }

    public static void main(String[] args) throws IOException {

        Command ob = new Command();
        ob.command();
    }
}
