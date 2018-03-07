package com.example.christina.avayadl.RSRPBackendSupport;

import org.researchsuite.rsrp.CSVBackend.CSVEncodable;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Christina on 2/6/2018.
 */

public class MEDLSpotRawCSVEncodable extends MEDLSpotRaw implements CSVEncodable {

    public MEDLSpotRawCSVEncodable(UUID uuid, String taskIdentifier, UUID taskRunUUID, Map<String, Object> schemaID, String[] selected, String[] notSelected, String[] excluded, Map<String, String> resultMap) {
        super(uuid, taskIdentifier, taskRunUUID, schemaID, selected, notSelected, excluded, resultMap);
    }

    public static String TYPE = "MEDLSpotRawCSVEncodable";

    @Override
    public String[] toRecords() {
        return new String[0];
    }

    @Override
    public String getTypeString() {
        return null;
    }

    @Override
    public String getHeader() {
        return null;
    }
}
