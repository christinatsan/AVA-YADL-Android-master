package com.example.christina.avayadl.RSRPBackendSupport;

import org.apache.commons.lang3.StringUtils;
import org.researchsuite.rsrp.CSVBackend.CSVEncodable;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import com.example.christina.avayadl.RSRPBackendSupport.YADLFullRaw;

/**
 * Created by Christina on 2/1/2018.
 */

public class YADLFullRawCSVEncodable extends YADLFullRaw implements CSVEncodable {

    public YADLFullRawCSVEncodable(UUID uuid, String taskIdentifier, UUID taskRunUUID, Map<String, Object> schemaID, Map<String, String> resultMap) {
        super(uuid, taskIdentifier, taskRunUUID, schemaID, resultMap);
    }

    public static String TYPE = "YADLFullRawCSVEncodable";

    @Override
    public String[] toRecords() {

        String time = "time"; // TODO: get time now for this field
        Collection resultMapValuesCollection = this.getResultMap().values();
        String[] resultMapValues = Arrays.copyOf(resultMapValuesCollection.toArray(), resultMapValuesCollection.toArray().length, String[].class);

        String resultMapJoined = StringUtils.join(resultMapValues,",");
        String resultMapWithTime = getTimestamp() + "," + resultMapJoined;
        String[] resultMapJoinedArray = new String[]{resultMapWithTime};

        return resultMapJoinedArray;
    }

    @Override
    public String getTypeString() {
        return this.getTaskIdentifier();
    }

    @Override
    public String getHeader() {

        String[] yadlHeader = new String[]{"timestamp","BedToChair","Dressing","Housework","Lifting","Shopping","ShortWalk","WalkingUpStairs"};
        String yadlHeaderJoined = StringUtils.join(yadlHeader,",");
        return yadlHeaderJoined;
    }

    private String getTimestamp () {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int zone = calendar.get(Calendar.ZONE_OFFSET);

        StringBuilder timestampBuilder = new StringBuilder();
        timestampBuilder.append(year);
        timestampBuilder.append("-");
        timestampBuilder.append(month);
        timestampBuilder.append("-");
        timestampBuilder.append(date);
        timestampBuilder.append("T");
        timestampBuilder.append(hour);
        timestampBuilder.append(":");
        timestampBuilder.append(minute);
        timestampBuilder.append(":");
        timestampBuilder.append(second);
        timestampBuilder.append("-");
        timestampBuilder.append(zone);

        String timestamp = timestampBuilder.toString();

        return timestamp;
    }

}
