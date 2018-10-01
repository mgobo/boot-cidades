package com.boot.cidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class BootCidadesApplication extends SpringBootServletInitializer{	
	
	public static void main(String[] args) throws SQLException {
		String sql			  = "INSERT INTO CIDADES "
				   			   +"VALUES(?,?,?,?,?,?,?,?,?,?)";
		String sql2			  = "SELECT COUNT(*) TOTAL FROM CIDADES";
		
		int totalRegistros	  = 0;
		ClassPathResource resourceFile 		 = new ClassPathResource("cidades.csv");
		ClassPathResource resourceConnection = new ClassPathResource("application.properties");		
		
		String host 	= ""; 
		String password = "";		
		try {
			host 					= resourceFile.getURL().getPath();
			Properties properties 	= new Properties();
			InputStream in 			= new FileInputStream(resourceConnection.getFile());
	        properties.load(in);	        
	        password 				= properties.getProperty("spring.datasource.password");
		}catch(IOException ex) {
			System.out.println("Error on read application.properties, [Error] = "+ex.getMessage());
		}
		
		try(Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cidades", "postgres", password)){
			try(Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql2)){
				while(rs.next()) {
					totalRegistros = rs.getInt("TOTAL");
				}
			}catch(SQLException ex) {
				System.out.println("Falha na abertura do socket com banco de dados...");
			}
			
			int rows = 0;
			if(totalRegistros == 0) {
				try(PreparedStatement pstmt = connection.prepareStatement(sql)){
					connection.setAutoCommit(false);
					File f = new File(host);
					try(FileReader fr = new FileReader(f); BufferedReader br = new BufferedReader(fr)){
						String line;
						int countLine = 0;
						while((line = br.readLine())!=null) {
							if(countLine > 0) {
								String[] splitLine = line.split(",");							
								Long ibge 				 = new Long(splitLine[0] != null && !splitLine[0].equals("") ? splitLine[0] : "0");
								String uf 				 = splitLine[1];
								String name 			 = splitLine[2];
								boolean capital 		 = new Boolean(splitLine[3] != null && !splitLine[3].equals("") ? splitLine[3] : "false").booleanValue();
								Double lat				 = new Double(splitLine[4] != null && !splitLine[4].equals("") ? splitLine[4] : "0");
								Double lon				 = new Double(splitLine[5] != null && !splitLine[5].equals("") ? splitLine[5] : "0");
								String no_accents 		 = splitLine[6];
								String alternative_names = splitLine[7] != null && !splitLine[7].equals("") ? splitLine[7] : "";
								String microregion 		 = splitLine[8];
								String mesoregion 		 = splitLine[9];
															
								
								pstmt.setLong(1, ibge);
								pstmt.setString(2, alternative_names);
								pstmt.setBoolean(3, capital);
								pstmt.setDouble(4, lat);
								pstmt.setDouble(5, lon);
								pstmt.setString(6, microregion);
								pstmt.setString(7, name);
								pstmt.setString(8, no_accents);
								pstmt.setString(9, uf);
								pstmt.setString(10, mesoregion);
								
								try {
									rows = rows + pstmt.executeUpdate();
									if(rows == 0) {
										throw new SQLException("Erro na carga do arquivo csv...");																									
									}
								}catch(Exception ex) {
									rows = -1;
									throw new SQLException(ex.getMessage());
								}
							}
							countLine++;
						}
					}catch(IOException ex) {
						System.out.println("Erro na carga do arquivo csv...");
					}
				}catch(SQLException ex) {
					System.out.println("Falha na abertura do socket com banco de dados...");
					connection.rollback();
				}				
				System.out.println("Criando dados...");
				connection.commit();				
			} else {
				System.out.println("Cidades ja estao na tabela...");
			}
		}catch(SQLException ex) {
			System.out.println("Falha na conexao com banco de dados...");
		}	
		SpringApplication.run(BootCidadesApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		System.out.println("Deploy app...");
		return builder.sources(APPLICATION_CLASS);
	}	
	
	private static Class<BootCidadesApplication> APPLICATION_CLASS = BootCidadesApplication.class;
}
