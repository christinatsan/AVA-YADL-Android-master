package com.example.christina.avayadl.RSRPBackendSupport;

import org.researchsuite.rsrp.CSVBackend.CSVEncodable;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Christina on 2/6/2018.
 */

public class MEDLFullRawCSVEncodable extends MEDLFullRaw implements CSVEncodable {

    public MEDLFullRawCSVEncodable(UUID uuid, String taskIdentifier, UUID taskRunUUID, Map<String, Object> schemaID, Map<String, String> resultMap) {
        super(uuid, taskIdentifier, taskRunUUID, schemaID, resultMap);
    }

    public static String TYPE = "MEDLFullRawCSVEncodable";

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
