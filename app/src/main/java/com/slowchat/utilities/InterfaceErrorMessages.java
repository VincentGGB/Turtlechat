/*
        TurtleChat
        Copyright (C) 2023  TurtleChat Open Source Community

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.slowchat.utilities;

public interface InterfaceErrorMessages {

    enum EventType {
        // BDD-related events
        CONNECTION_FAILED,

        // Message-related events
        MESSAGE_SIZE, MESSAGE_INSERT_FAILED, MESSAGE_DELETE_FAILED, MESSAGE_SEND_FAILED,

        // Receipt-related events
        RECEIPT_SIZE, RECEIPT_CREATION_FAILED, RECEIPT_DELETE_FAILED,

        // Contact-related events
        CONTACT_CREATION_FAILED, CONTACT_DELETE_FAILED, CONTACT_UPDATE_FAILED,

        // App data-related events
        APPDATA_CREATION_FAILED, APPDATA_UPDATE_FAILED,

        // Bluetooth-related events
        NO_BLUETOOTH,

        // Socket-related events
        SOCKET_READ_FAILED, SOCKET_WRITE_FAILED,

        // GPS-related events
        NO_GPS,

        // Cartography-related events
        CARTO_NULL_OSM_OBJECT, CARTO_WRONG_ARGUMENT,

        // Vault-related events
        VAULT_CREATION_FAILED, VAULT_REPLACE_FAILED, VAULT_TOTAL_SIZE, VAULT_DELETE_FAILED, VAULT_DOWNLOAD_FAILED, VAULT_FILE_SIZE,

        // Letterbox-related events
        LETTERBOX_EDIT_FAILED, LETTERBOX_DELETE_FAILED, LETTERBOX_CREATION_FAILED, LETTERBOX_GET_FAILED;

        public String getEventLabel(){
            switch (this) {
                case CONNECTION_FAILED:
                    return "Base de données inaccessible";
                case MESSAGE_SIZE:
                    return "Taille de mémoire dépassée";
                case MESSAGE_INSERT_FAILED:
                    return "Erreur d’insertion d’un message en BDD";
                case MESSAGE_DELETE_FAILED:
                    return "Erreur de suppression d’un message en BDD";
                case MESSAGE_SEND_FAILED:
                    return "Erreur on send message";
                case RECEIPT_SIZE:
                    return "Taille de mémoire dépassée";
                case RECEIPT_CREATION_FAILED:
                    return "Erreur d’insertion d’un accusé de reception en BDD";
                case RECEIPT_DELETE_FAILED:
                    return "Erreur de suppression d’un accusé de reception en BDD";
                case CONTACT_CREATION_FAILED:
                    return "Erreur d’insertion d’un contact en BDD";
                case CONTACT_DELETE_FAILED:
                    return "Erreur de suppression d’un contact en BDD";
                case CONTACT_UPDATE_FAILED:
                    return "Erreur de mise à jour d’un contact en BDD";
                case APPDATA_CREATION_FAILED:
                    return "Erreur d’insertion d’une kley/valeur dans AppData sharedpreferences";
                case APPDATA_UPDATE_FAILED:
                    return "Erreur de mise à jour d’une valeur dans AppData sharedpreferences";
                case NO_BLUETOOTH:
                    return "Le Bluetooth n'est pas activé, ou les autorisations ne sont pas accordées";
                case SOCKET_READ_FAILED:
                    return "Erreur de lecture du socket";
                case SOCKET_WRITE_FAILED:
                    return "Erreur d'écriture du socket";
                case NO_GPS:
                    return "Le GPS n'est pas activé, ou les autorisations ne sont pas accordées";
                case CARTO_NULL_OSM_OBJECT:
                    return "Objet OSModel non initialisé";
                case CARTO_WRONG_ARGUMENT:
                    return "Argument d’un type inattendu";
                case VAULT_CREATION_FAILED:
                    return "La création du Vault a échoué, la clef entré par l’utilisateur est déjà utilisée";
                case VAULT_REPLACE_FAILED:
                    return "Le Remplacement a échoué, la clef entré par l’utilisateur n’existe pas";
                case VAULT_TOTAL_SIZE:
                    return "La boite aux lettre à atteind le stockage maximum";
                case VAULT_DELETE_FAILED:
                    return "La Suppression a échoué, la clef entré par l’utilisateur n’existe pas";
                case VAULT_DOWNLOAD_FAILED:
                    return "Le téléchargement a échoué, la clef entré par l’utilisateur n’existe pas";
                case VAULT_FILE_SIZE:
                    return "Le fichier entré par l’utilisateur excède la limite autorisée";
                case LETTERBOX_EDIT_FAILED:
                    return "Impossible de sauvegarder les modifications";
                case LETTERBOX_DELETE_FAILED:
                    return "Suppression impossible, la boite aux lettres n'existe pas";
                case LETTERBOX_GET_FAILED:
                    return "Aucune boite aux lettres enregistrées";
                case LETTERBOX_CREATION_FAILED:
                    return "Error on create LetterBox";
                default:
                    return "Erreur inconnue";
            }
        }
    }
}
