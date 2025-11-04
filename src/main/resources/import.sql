INSERT INTO rol (tipo) VALUES ('ADMINISTRADOR');
INSERT INTO rol (tipo) VALUES ('MODERADOR');
INSERT INTO rol (tipo) VALUES ('CLIENTE');

INSERT INTO plan (nombre, costo, duracion_dias, porcentaje_extra, estado) VALUES ('Plan Basico', 9.99, 30, 0.0, 'Activo');
INSERT INTO plan (nombre, costo, duracion_dias, porcentaje_extra, estado) VALUES ('Plan Premium', 19.99, 90, 5.0, 'Activo');
INSERT INTO plan (nombre, costo, duracion_dias, porcentaje_extra, estado) VALUES ('Plan Anual', 49.99, 365, 10.0, 'Activo');

INSERT INTO usuario (nombre, apellido, correo, password, edad, estado, ecobits, id_rol, plan_id) VALUES ('Carlos', 'Admin', 'admin@eco.com', '$2a$12$MH7yW.pGW6vVruvgKqRHS.xm3VN2WVWsnBfNokHm/8hW/nEv9T8FK', 30, 'ACTIVO', 10000, 1, 1);
INSERT INTO usuario (nombre, apellido, correo, password, edad, estado, ecobits, id_rol, plan_id) VALUES ('Lucía', 'Moderadora', 'moderador@eco.com', '$2a$12$/lYIOo0DTTIpeJBx7g56GOmJIKEPqFnWZRccrzmwhdwqWy64cdNCi', 28, 'ACTIVO', 5000, 2, 1);
INSERT INTO usuario (nombre, apellido, correo, password, edad, estado, ecobits, id_rol, plan_id) VALUES ('Pedro', 'Cliente', 'cliente@eco.com', '$2a$12$3sv2KMRt8mSa59ts8gJ/hehjSurbtoGiLDa1.H5DO5opt/HoLT4Gm', 25, 'ACTIVO', 2000, 3, 1);
INSERT INTO usuario (nombre, apellido, correo, password, edad, estado, ecobits, id_rol, plan_id) VALUES ('Ana', 'Premium', 'premium@eco.com', '$2a$12$uZbxt/nURv2wzumBz7E44O8a6I4Sy3Rt8gvP/CL1cuV97FxfVBAZW', 32, 'ACTIVO', 2000, 3, 2);
INSERT INTO usuario (nombre, apellido, correo, password, edad, estado, ecobits, id_rol, plan_id) VALUES ('Pepe', 'Moderador', 'moderador2@eco.com', '$2a$12$Doefkd1pNlqSx9bfSURtfu7g06YvPRyQ.OMXAfQyDy.vjm1D9GZDO', 27, 'ACTIVO', 3000, 2, 1);

INSERT INTO empresa (nombre, categoria, descripcion, estado, usuario_id) VALUES ('EcoChips SAC', 'Reciclaje Electrónico', 'Empresa dedicada a la recolección y reciclaje de chips electrónicos.', 'Activo', 1);
INSERT INTO empresa (nombre, categoria, descripcion, estado, usuario_id) VALUES ('GreenBite Perú', 'Alimentos Saludables', 'Startup que produce snacks naturales a base de frutas deshidratadas y granos andinos, 100% libres de conservantes.', 'Activo', 1);
INSERT INTO empresa (nombre, categoria, descripcion, estado, usuario_id) VALUES ('VitaPack SAC', 'Empaques Biodegradables', 'Empresa que fabrica envases y empaques compostables elaborados con almidón de maíz y fibra vegetal, ideales para negocios sostenibles.', 'Activo', 1);
INSERT INTO empresa (nombre, categoria, descripcion, estado, usuario_id) VALUES ('SolarLife EIRL', 'Energías Renovables', 'Compañía dedicada a la instalación de paneles solares residenciales y empresariales para reducir la huella de carbono.', 'Activo', 1);

INSERT INTO producto (nombre, categoria, precio, stock, estado, empresa_id) VALUES ('Mouse Reciclado', 'Accesorios', 25.00, 5, 'Activo', 1);
INSERT INTO producto (nombre, categoria, precio, stock, estado, empresa_id) VALUES ('Teclado Ecológico', 'Accesorios', 45.00, 3, 'Inactivo', 1);
INSERT INTO producto (nombre, categoria, precio, stock, estado, empresa_id) VALUES ('Pendrive Reutilizado 32GB', 'Almacenamiento', 30.00, 10, 'Activo', 1);
INSERT INTO producto (nombre, categoria, precio, stock, estado, empresa_id) VALUES ('Snack de Quinoa y Frutas', 'Snacks Naturales', 15.00, 20, 'Inactivo', 2);
INSERT INTO producto (nombre, categoria, precio, stock, estado, empresa_id) VALUES ('Barra Energética de Cacao Orgánico', 'Snacks Naturales', 9.00, 25, 'Activo', 2);
INSERT INTO producto (nombre, categoria, precio, stock, estado, empresa_id) VALUES ('Pack de Mix Andino (Kiwicha, Chía y Pasas)', 'Alimentos Saludables', 15.00, 15, 'Activo', 2);
INSERT INTO producto (nombre, categoria, precio, stock, estado, empresa_id) VALUES ('Caja Compostable Mediana', 'Empaques', 4.00, 40, 'Activo', 3);
INSERT INTO producto (nombre, categoria, precio, stock, estado, empresa_id) VALUES ('Bolsa Biodegradable 10L', 'Empaques', 2.00, 100, 'Activo', 3);
INSERT INTO producto (nombre, categoria, precio, stock, estado, empresa_id) VALUES ('Vaso Ecológico de Maíz 12oz', 'Utensilios', 2.00, 80, 'Activo', 3);
INSERT INTO producto (nombre, categoria, precio, stock, estado, empresa_id) VALUES ('Panel Solar Residencial 300W', 'Energía Solar', 650.00, 6, 'Activo', 4);
INSERT INTO producto (nombre, categoria, precio, stock, estado, empresa_id) VALUES ('Kit Solar Portátil', 'Energía Solar', 480.00, 8, 'Activo', 4);
INSERT INTO producto (nombre, categoria, precio, stock, estado, empresa_id) VALUES ('Lámpara LED Solar Exterior', 'Iluminación', 100.00, 20, 'Activo', 4);

INSERT INTO actividad (nombre, descripcion, recompensa, categoria, estado, usuario_id) VALUES ('Trote a la Avenida 3km', 'Trote individual o grupal en zonas seguras de la ciudad.', 20, 'Ejercicio', 'Activa', 1);
INSERT INTO actividad (nombre, descripcion, recompensa, categoria, estado, usuario_id) VALUES ('Ciclismo Urbano 5km', 'Realiza un recorrido en bicicleta por tu distrito fomentando la movilidad sostenible.', 30, 'Ejercicio', 'Activa', 1);
INSERT INTO actividad (nombre, descripcion, recompensa, categoria, estado, usuario_id) VALUES ('Caminata Familiar 4km', 'Participa con tu familia en una caminata ecológica por parques o malecones.', 25, 'Ejercicio', 'Activa', 1);
INSERT INTO actividad (nombre, descripcion, recompensa, categoria, estado, usuario_id) VALUES ('Recolección de Residuos en el Parque', 'Ayuda a mantener los espacios públicos limpios recolectando residuos.', 40, 'Ecología', 'Activa', 1);
INSERT INTO actividad (nombre, descripcion, recompensa, categoria, estado, usuario_id) VALUES ('Plantación de Árboles Locales', 'Participa en la siembra de árboles en zonas urbanas.', 50, 'Ecología', 'Activa', 1);
INSERT INTO actividad (nombre, descripcion, recompensa, categoria, estado, usuario_id) VALUES ('Reciclaje en Casa', 'Clasifica correctamente residuos reciclables y llévalos a un punto de acopio.', 15, 'Reciclaje', 'Activa', 1);
INSERT INTO actividad (nombre, descripcion, recompensa, categoria, estado, usuario_id) VALUES ('Charla Virtual: Energías Renovables', 'Asiste a una charla virtual sobre energía solar y ahorro energético.', 20, 'Educación Ambiental', 'Activa', 1);
INSERT INTO actividad (nombre, descripcion, recompensa, categoria, estado, usuario_id) VALUES ('Taller de Compostaje Casero', 'Aprende a crear compost con desechos orgánicos de tu hogar.', 30, 'Educación Ambiental', 'Activa', 1);
INSERT INTO actividad (nombre, descripcion, recompensa, categoria, estado, usuario_id) VALUES ('Participación en Feria Verde', 'Únete como voluntario en ferias ecológicas promoviendo productos sostenibles.', 35, 'Voluntariado', 'Activa', 1);

INSERT INTO solicitud (id_moderador, id_admin, nombre_comunidad, ubicacion, descripcion, estado, fecha_creacion, fecha_revision) VALUES (5, null, 'EcoDeportistas Lima', 'San Borja', 'Grupo de corredores y ciclistas que canjean puntos por productos ecológicos.', 'Activo', '2025-10-05', '2025-10-05');

INSERT INTO comunidad (id_moderador, idsolicitud, estado, ubicacion, nombre, descripcion) VALUES (5, 1, 'Aprobada', 'San Borja', 'EcoDeportistas Lima', 'Grupo de corredores y ciclistas que canjean puntos por productos ecológicos.');

INSERT INTO evento (nombre, descripcion, ubicacion, fecha, organizador, beneficios, recompensa, estado, comunidad_id) VALUES ('Reciclatón Universitaria', 'Campaña de reciclaje de botellas y chips electrónicos dentro del campus.', 'Monterrico - Patio Central', '2025-10-10', 'Comunidad UPC', 'Acumula EcoBits y colabora con el medio ambiente.', 50, 'Activo', 1);
INSERT INTO evento (nombre, descripcion, ubicacion, fecha, organizador, beneficios, recompensa, estado, comunidad_id) VALUES ('Día Verde UPC', 'Evento ecológico con talleres sobre reducción de residuos y movilidad sostenible.', 'Auditorio Principal UPC', '2025-10-15', 'Comunidad UPC', 'Descuentos en productos ecológicos y reconocimiento digital.', 40, 'Activo', 1);

INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (4, '2025-11-04 04:03:36.256730',  7, 1, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (25, '2025-11-04 04:03:43.522282', , 1, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (30, '2025-11-04 04:03:47.197806',  3, 1, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (2, '2025-11-04 04:03:49.429174',  8, 1, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (650, '2025-11-04 04:03:59.436910',  10, 1, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (4, '2025-11-04 04:04:09.866382',  7, 1, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (650, '2025-11-04 04:04:14.717953',  10, 2, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (30, '2025-11-04 04:04:21.991862',  3, 2, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (2, '2025-11-04 04:04:24.930347',  8, 2, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (9, '2025-11-04 04:04:30.028154', 5, 3, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (480, '2025-11-04 04:04:32.842514',  11, 3, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (100, '2025-11-04 04:04:35.269749',  12, 3, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (25, '2025-11-04 04:05:10.053553',  1, 4, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (25, '2025-11-04 04:05:12.637059',  1, 4, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (480, '2025-11-04 04:05:27.250404',  11, 1, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (480, '2025-11-04 04:05:28.634349',  11, 1, 'Completado');
INSERT INTO usuario_producto (ecobits, fecha, producto_id, usuario_id, estado) VALUES (480, '2025-11-04 04:05:29.532961',  11, 1, 'Completado');