package models;

import javax.tools.*;
import java.io.*;
import java.util.*;
import java.util.jar.*;
import org.stringtemplate.v4.*;
import org.stringtemplate.v4.misc.*;
import play.Logger;

public class javaParse {

    public static void main(List<Relationship> relationships) {
        try {
            run(relationships);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String run(List<Relationship> relationships) throws IOException {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS,"NodeOutput");
        JarOutputStream target = new JarOutputStream(new FileOutputStream("/tmp/NodeOutput.jar"), manifest);

        classCompile(createJavaFile(relationships));

        add(new File("/tmp/NodeOutput.class"), target); //Placeholder until java file is generated
        //write to file "System.lnOut("$node1$ linked to $node2$") or something similar using stringTemplate

        target.close();
        return "/tmp/NodeOutput.jar";
    }
        
    public static File createJavaFile(List<Relationship> relationships) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        File sourceFile = new File("/tmp/NodeOutput.java");
        sourceFile.createNewFile();
        ErrorBuffer errBuf = new ErrorBuffer();

        String beginning = "class NodeOutput{public static void main(String[] args){System.out.println(\"";
        stringBuilder.append(beginning);

        for (Relationship r: relationships) {
            String middle = r.toString() + "\\n";
            stringBuilder.append(middle);
        }

        String end = "\");}}";
        stringBuilder.append(end);
        Logger.info(stringBuilder.toString());

        ST source = new ST(stringBuilder.toString());
        source.inspect();

        source.write(sourceFile, errBuf);

        return sourceFile;
    }
        
    //To compile a java file into a class file for executing in the .jar file
    //possibly return as a file from cache
    public static void classCompile(File fileToCompile) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //StandardJavaFileManager = fileManager = compiler.getStandardFileManager(null, null, null);
        int compilationResult = compiler.run(null, null, null, fileToCompile.getPath());
        if (compilationResult==0) {
            System.out.println("Success.");
        } else {
            System.out.println("Failed.");
        }
    }
        
    //parse json to retrieve attributes
    /*public static void jsonParse(JsonNode json){
        *//*json.get();*//*
    }*/
    
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

        JarEntry entry = new JarEntry(source.getName());
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