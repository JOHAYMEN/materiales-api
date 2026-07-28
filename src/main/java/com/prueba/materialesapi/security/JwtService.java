package com.prueba.materialesapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
/**
 * Servicio encargado de la generación y validación de tokens JWT.
 *
 * <p>
 * Proporciona los métodos necesarios para crear un token de autenticación
 * a partir del nombre de usuario y para extraer la información contenida
 * en un token previamente generado.
 * </p>
 *
 * <p>
 * Los tokens son firmados utilizando el algoritmo HS256 y tienen una
 * vigencia de 24 horas.
 * </p>
 *
 * @author Johaymen Álvarez Romero
 * @since 1.0
 */
@Service
public class JwtService {

    /**
     * Clave secreta utilizada para firmar y validar los tokens JWT.
     */
    private static final String SECRET_KEY =
            "materiales-api-secret-key-materiales-api-secret-key";

    /**
     * Clave criptográfica utilizada por la librería JWT para la firma
     * de los tokens.
     */
    private final Key key =
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * Genera un token JWT para el usuario autenticado.
     *
     * <p>
     * El token contiene el nombre del usuario, la fecha de emisión y una
     * fecha de expiración de 24 horas.
     * </p>
     *
     * @param username nombre del usuario autenticado.
     * @return token JWT firmado.
     */
    public String generateToken(String username){

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()+86400000)
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    /**
     * Extrae el nombre del usuario contenido en un token JWT.
     *
     * @param token token JWT enviado por el cliente.
     * @return nombre del usuario autenticado.
     */
    public String extractUsername(String token){

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

}