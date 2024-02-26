package com.dummyRecorder.negocio;

import com.dummyRecorder.model.Transaccion;
import com.dummyRecorder.model.TransaccionRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Procesa una transacción
 */
@Slf4j
public class ProcesadorTx {

    private String datasourceId;
    private Connection connection;
    public ProcesadorTx(String datasourceId) throws SQLException, NamingException {
        this.datasourceId = datasourceId;
        Connect();
    }

    private void Connect() throws NamingException, SQLException {
        Context ctx = new InitialContext();
        DataSource dataSource = (DataSource) ctx.lookup(datasourceId);
        this.connection = dataSource.getConnection();
    }

    public void grabaTx(String tx) throws JsonProcessingException {
        Transaccion transaccion = TransaccionRecord.parseJson(tx);
        if(isTxidExists(transaccion.getTxid()))
            deleteTxFromDatabase(transaccion.getTxid());
        insertTxIntoDatabase(tx);
    }

    private boolean isTxidExists(String txid) {
        boolean exists = false;
        try {
            // Consulta SQL para verificar si el txid existe en la tabla
            String query = "SELECT COUNT(*) FROM Transacciones WHERE txid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, txid);
            ResultSet resultSet = statement.executeQuery();
            // Si la consulta devuelve un resultado mayor que cero, significa que el txid existe
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                exists = count > 0;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error al verificar si el txid existe en la base de datos: " + e.toString());
        }
        return exists;
    }

    private void deleteTxFromDatabase(String txid) {
        try {
            // Consulta SQL para eliminar el registro con el txid especificado
            String query = "DELETE FROM Transacciones WHERE txid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, txid);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error al eliminar el registro con el txid " + txid + " de la base de datos: " + e.toString());
        }
    }


    public void insertTxIntoDatabase(String tx) {
        try  {
            String insertQuery = TransaccionRecord.generateInsertStatement(TransaccionRecord.parseJson(tx));
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.executeUpdate();
            log.info("Transacción insertada");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error al insertar el registro en la base de datos: " + e.toString() + " -> " + tx);
        }
    }

}

