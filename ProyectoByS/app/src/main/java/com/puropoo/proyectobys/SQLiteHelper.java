package com.puropoo.proyectobys;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class SQLiteHelper extends SQLiteOpenHelper {

    // Definir el nombre y la versión de la base de datos
    private static final String DATABASE_NAME = "service_db";
    private static final int DATABASE_VERSION = 3;
    
    // Definir constante SQL para crear la tabla equipo_instalar
    private static final String CREATE_EQUIPO_INSTALAR_TABLE = "CREATE TABLE IF NOT EXISTS equipo_instalar (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "request_id INTEGER, " +
            "equipo_nombre TEXT, " +
            "clientCedula TEXT)";

    // Definir constante SQL para crear la tabla second_visit
    private static final String CREATE_SECOND_VISIT_TABLE = "CREATE TABLE IF NOT EXISTS second_visit (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "request_id INTEGER NOT NULL, " +
            "visit_date TEXT NOT NULL, " +
            "visit_time TEXT NOT NULL, " +
            "FOREIGN KEY(request_id) REFERENCES requests(id))";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla 'clients'
        db.execSQL("CREATE TABLE IF NOT EXISTS clients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, cedula TEXT, phone TEXT, address TEXT, serviceType TEXT)");

        // Crear la tabla 'team_members' con la columna 'clientCedula'
        String CREATE_TEAM_MEMBERS_TABLE = "CREATE TABLE IF NOT EXISTS team_members (" +
                "id INTEGER PRIMARY KEY," +
                "technician_role TEXT," +
                "technician_name TEXT," +
                "technician_phone TEXT," +
                "team_members_count INTEGER)";
        db.execSQL(CREATE_TEAM_MEMBERS_TABLE);

        // Crear tabla 'requests' con la nueva columna 'serviceAddress'
        db.execSQL("CREATE TABLE IF NOT EXISTS requests (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "serviceType TEXT, " +
                "serviceDate TEXT, " +
                "serviceTime TEXT, " +
                "clientCedula TEXT, " + // Asegurarnos de tener la cédula del cliente
                "serviceAddress TEXT);"); // Columna para la dirección de la solicitud

        // Crear tabla 'team' (falta esta tabla)
        db.execSQL("CREATE TABLE IF NOT EXISTS team (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "request_id INTEGER, " + // Relación con la solicitud
                "technician_name TEXT, " +
                "technician_role TEXT, " +
                "technician_phone TEXT);");

        // Crear la tabla 'maintenance_requirements'
        String CREATE_MAINTENANCE_REQUIREMENTS_TABLE = "CREATE TABLE IF NOT EXISTS maintenance_requirements (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "serviceName TEXT, " +
                "requirements TEXT)";
        db.execSQL(CREATE_MAINTENANCE_REQUIREMENTS_TABLE);

        // Crear la tabla 'equipo_instalar'
        db.execSQL(CREATE_EQUIPO_INSTALAR_TABLE);

        // Crear la tabla 'second_visit'
        db.execSQL(CREATE_SECOND_VISIT_TABLE);

    }

    // Método para actualizar la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejar la actualización de versión 1 a 2
        if (oldVersion < 2) {
            // Crear la tabla 'equipo_instalar' para la actualización de versión 1 a 2
            db.execSQL("DROP TABLE IF EXISTS equipo_instalar");
            db.execSQL(CREATE_EQUIPO_INSTALAR_TABLE);
        }
        
        // Manejar la actualización de versión 2 a 3
        if (oldVersion < 3) {
            // Crear la tabla 'second_visit' para la actualización de versión 2 a 3
            db.execSQL(CREATE_SECOND_VISIT_TABLE);
        }
    }

}
