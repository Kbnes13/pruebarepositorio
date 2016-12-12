package accesobbddejemplo;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author edukb
 */
public class AccesoBBDDejemplo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ejecutar;
        int menu=-1;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            final Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/practica?autoReconnect=true&useSSL=false","root","1234");
            final Statement sentencia = conexion.createStatement();
            ResultSet resultado;
            do{
                System.out.println("Escoja una opción del menu (0-->Listar 1-->Insertar 2-->Editar 3-->Eliminar 4-->Salir)");
                menu = scanner.nextInt();
            
                switch(menu){
                    case 0:
                        System.out.println("Escoja la tabla a listar (0-->Empleados 1-->Departamentos)");
                        int tabla = scanner.nextInt();
                        
                        
                        if(tabla==0){   //Empleados
                            resultado = sentencia.executeQuery("SELECT * FROM EMPLEADOS");
                            listar(resultado,tabla);
                            resultado.close();
                        }
                        if(tabla==1){   //Departamentos
                            resultado = sentencia.executeQuery("SELECT * FROM DEPARTAMENTOS");
                            listar(resultado,tabla);
                            resultado.close();
                        }
                        System.out.println("----------------------------------");
                        break;

                    case 1:
                        System.out.println("Escoja la tabla para insertar (0-->Empleados 1-->Departamentos)");
                        int tabla3 = scanner.nextInt();
                        ejecutar=insertar(tabla3);
                        
                        try{
                            int numfilas = sentencia.executeUpdate(ejecutar);
                            if(numfilas>0)System.out.println("Datos insertados correctamente");
                        }catch(Exception e){
                            System.out.println("Fallo al insertar");
                        }
                        break;

                    case 2:
                        System.out.println("Escoja la tabla para editar (0-->Empleados 1-->Departamentos)");
                        int tabla2 = scanner.nextInt();
                        if(tabla2==0){   //Empleados
                            resultado = sentencia.executeQuery("SELECT * FROM EMPLEADOS");
                            listar(resultado,tabla2);
                            resultado.close();
                        }
                        if(tabla2==1){   //Departamentos
                            resultado = sentencia.executeQuery("SELECT * FROM DEPARTAMENTOS");
                            listar(resultado,tabla2);
                            resultado.close();
                        }
                        ejecutar=editar(tabla2);
                        
                        try{
                            int numfilas = sentencia.executeUpdate(ejecutar);
                            if(numfilas>0)System.out.println("Datos editados correctamente");
                        }catch(Exception e){
                            e.printStackTrace();
                            System.out.println("Fallo al editar");
                        }
                        
                        break;

                    case 3:
                        System.out.println("Escoja la tabla para eliminar (0-->Empleados 1-->Departamentos)");
                        int tabla4 = scanner.nextInt();
                        if(tabla4==0){   //Empleados
                            resultado = sentencia.executeQuery("SELECT * FROM EMPLEADOS");
                            listar(resultado,tabla4);
                            resultado.close();
                        }
                        if(tabla4==1){   //Departamentos
                            resultado = sentencia.executeQuery("SELECT * FROM DEPARTAMENTOS");
                            listar(resultado,tabla4);
                            resultado.close();
                        }
                        
                        ejecutar=eliminar(tabla4);
                        try{
                            int numfilas = sentencia.executeUpdate(ejecutar);
                            if(numfilas>0)System.out.println("Datos eliminados correctamente");
                        }catch(Exception e){
                            e.printStackTrace();
                            System.out.println("Fallo al editar");
                        }
                        break;
                        
                }
            }while(menu!=4);
            sentencia.close();
            conexion.close();
        }catch(Exception e){
            System.out.println("Fallo en el driver");
        }
    }
    
    //Metodo para listar datos de las tablas
    static void listar(ResultSet resultado, int tabla){
        if(tabla==0){   //Empleados
            try{
                while(resultado.next()){
                    System.out.println("----------------------------------");
                    System.out.println("Nº Empleado: "+resultado.getInt("empno"));
                    System.out.println("Apellido: "+resultado.getString("apellido"));
                    System.out.println("Oficio: "+resultado.getString("oficio"));
                    System.out.println("Fecha de alta: "+resultado.getDate("fecha_alta"));
                    System.out.printf("Salario: %.2f€\n",resultado.getFloat("salario"));
                }
            }catch(Exception e){
                System.out.println("Error");
            }
        }
        if(tabla==1){   //Departamentos
            try{
                while(resultado.next()){
                    System.out.println("----------------------------------");
                    System.out.println("Nº Departamento: "+resultado.getInt(1));
                    System.out.println("Nombre de departamento: "+resultado.getString("dnombre"));
                    System.out.println("Localización: "+resultado.getString("loc"));
                }
            }catch(Exception e){
                System.out.println("Error");
            }
        }
    }
    
    //Metodo para insertar datos en las tablas
    static String insertar(int tabla){
        Scanner scanner = new Scanner(System.in);
        if(tabla==0){   //Empleados
            System.out.println("Introduzca el apellido del empleado");
            String apellido = scanner.next();
            System.out.println("Introduzca el oficio del empleado");
            String oficio = scanner.next();
            System.out.println("Introduzca la fecha de alta del empleado");
            String alta = scanner.next();
            System.out.println("Introduzca el salario del empleado");
            float salario =scanner.nextFloat();

            String sal=String.valueOf(salario);

            String ejecutar = String.format("INSERT INTO EMPLEADOS (apellido,oficio,fecha_alta,salario) VALUES('%s','%s','%s',%.0f)", apellido, oficio, alta, salario);
            System.out.println(ejecutar);
            return ejecutar;
        }
        if(tabla==1){   //Departamentos  
            System.out.println("Introduzca el nombre del departamento");
            String dnombre = scanner.next();
            System.out.println("Introduzca la localizacion del departamento");
            String loc = scanner.next();
            System.out.println(loc);

            String ejecutar = String.format("INSERT INTO DEPARTAMENTOS (dnombre,loc) VALUES('%s','%s')", dnombre,loc);
            System.out.println(ejecutar);
            return ejecutar;
        }
        else{
            return("Tabla no reconocida");
        }
    }
    
    //Metodo para editar datos en las tablas
    static String editar(int tabla){
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------------");
        if(tabla==0){
            System.out.println("Introduzca el numero del empleado a editar");
            String empno = scanner.next();
            System.out.println("Introduzca el apellido del empleado");
            String apellido = scanner.next();
            System.out.println("Introduzca el oficio del empleado");
            String oficio = scanner.next();
            System.out.println("Introduzca la fecha de alta del empleado");
            String alta = scanner.next();
            System.out.println("Introduzca el salario del empleado");
            float salario =scanner.nextFloat();

            String sal=String.valueOf(salario);

            String ejecutar = String.format("UPDATE EMPLEADOS SET apellido='%s', oficio='%s', fecha_alta='%s', salario=%s WHERE empno=%s", apellido, oficio, alta, salario, empno);
            System.out.println(ejecutar);
            return ejecutar;
        }
        if(tabla==1){
            System.out.println("Introduzca el numero del departamento a editar");
            String dept_no = scanner.next();
            System.out.println("Introduzca el nombre del departamento");
            String dnombre = scanner.next();
            System.out.println("Introduzca la localizacion del departamento");
            String loc = scanner.next();
            System.out.println(loc);

            String ejecutar = String.format("UPDATE DEPARTAMENTOS SET dnombre='%s', loc='%s' WHERE deptno=%s", dnombre, loc, dept_no);
            System.out.println(ejecutar);
            return ejecutar;
        }else{
            return "Tabla no reconocida";
        }
    }
    
    //Metodo para eliminar datos de las tablas
    static String eliminar(int tabla){
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------------");
        if(tabla==0){
            System.out.println("Introduzca el numero del empleado a eliminar");
            String empno = scanner.next();
            String consulta = String.format("'%s'",tabla);
            String ejecutar = String.format("DELETE FROM EMPLEADOS WHERE empno="+empno);
            return ejecutar;
        }
        if(tabla==1){
            System.out.println("Introduzca el numero del departamento a eliminar");
            String deptno = scanner.next();
            String consulta = String.format("'%s'",tabla);
            String ejecutar = String.format("DELETE FROM DEPARTAMENTOS WHERE deptno="+deptno);
            return ejecutar;
        }else{
            return "Tabla no reconocida";
        }
    }
}
