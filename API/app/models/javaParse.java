package models;

import java.io.*;
import java.util.*;
import java.util.jar.*;

public class javaParse{
    public static void main(String[] args)
        {
        try{
            run();
            } catch (IOException e) {
            }
        }

    public static void run() throws IOException
        {
          Manifest manifest = new Manifest();
          manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0\n");
          manifest.getMainAttributes().put("Main-Class: NodeOutput\n");
          JarOutputStream target = new JarOutputStream(new FileOutputStream("output.jar"), manifest);
          
          classCompile
          
          add(new File("NodeOutput.class"), target); //Placeholder until java file is generated
          //write to file "System.lnOut("$node1$ linked to $node2$") or something similar using stringTemplate
          
          target.close();
        }
        
        public static file createJavaFile(){
            StringTemplate source = 
                new StringTemplate("class javaPrint{\npublic static void main(String[] args){\nSystem.out.println(\"$node1$ is connected to $node2$\");\n }\n}");
            source.setAttribute("node1","");
            source.setAttribute("node2","");
            new File("NodeOutput.java");
        }
        
        //To compile a java file into a class file for executing in the .jar file
        //possibly return as a file from cache
        public static void classCompile(file fileToCompile)
        {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            //StandardJavaFileManager = fileManager = compiler.getStandardFileManager(null, null, null);
            int compilationResult = compiler.run(null, null, null, fileToCompile.getPath());
            if (compilationResult==0){
                System.out.println("Success.");
            }   else {
                System.out.println("Failed.");
            }
        }
        
    //parse json to retrieve attributes
    public static void jsonParse(JsonNode json){
        json.get(
    }
    
    //Adds a file to a jaroutputstream
    private static void add(File source, JarOutputStream target) throws IOException
    {
      BufferedInputStream in = null;
      try
      {
        if (source.isDirectory())
        {
          String name = source.getPath().replace("\\", "/");
          if (!name.isEmpty())
          {
            if (!name.endsWith("/"))
              name += "/";
            JarEntry entry = new JarEntry(name);
            entry.setTime(source.lastModified());
            target.putNextEntry(entry);
            target.closeEntry();
          }
          for (File nestedFile: source.listFiles())
            add(nestedFile, target);
          return;
        }

        JarEntry entry = new JarEntry(source.getPath().replace("\\", "/"));
        entry.setTime(source.lastModified());
        target.putNextEntry(entry);
        in = new BufferedInputStream(new FileInputStream(source));

        byte[] buffer = new byte[1024];
        while (true)
        {
          int count = in.read(buffer);
          if (count == -1)
            break;
          target.write(buffer, 0, count);
        }
        target.closeEntry();
      }
      finally
      {
        if (in != null)
          in.close();
      }
    }
}
