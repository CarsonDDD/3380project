import Entities.*;
import Entities.Character;
import Entities.Interfaces.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonToOOP {

    //HashMap<String, Jutsu> jutsusAll = new HashMap<>();// key is value
    //HashMap<String, UniqueTrait> traitsAll = new HashMap<>(); // key is value
    //HashMap<Integer, Character> charactersAll = new HashMap<>();
    //HashMap<Integer, Clan> clansAll = new HashMap<>();
    //HashMap<Integer, KekkeiGenkai> kekkeiGenkaiAll = new HashMap<>();
    //HashMap<Integer, Team> teamsAll = new HashMap<>();
    //HashMap<Integer, Village> villagesAll = new HashMap<>();
    //HashMap<String, Media> mediasAll = new HashMap<>(); // Key is type+name
    //HashMap<String, Personal> personalAll = new HashMap<>(); // Key is type+value
    //HashMap<String, VoiceActor> voiceActorAll = new HashMap<>(); // Key is name+language
    //HashMap<String, Tool> toolsAll = new HashMap<>(); // key is tool name
    //HashMap<String, NatureType> natureTypeAll = new HashMap<>(); // key is value
    //HashMap<Integer, TailedBeast> tailedBeastsAll = new HashMap<>();
    //HashMap<Integer, Kara> karaAll = new HashMap<>();
    //HashMap<Integer, Akatsuki> akatsukiAll = new HashMap<>(); // not finished
    // Rank??!?!?!?!?!? TODO: later

    public BufferedReader fetchData(String endpoint) throws IOException {
        URL url = new URL(endpoint);

        // Attempt to get data
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setInstanceFollowRedirects(true);// magic?
        conn.setRequestMethod("GET"); // REQUEST
        conn.connect();

        // Check if the connection is successful
        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + responseCode);
        }

        return new BufferedReader(new InputStreamReader(conn.getInputStream()));
    }

    public JSONArray getJsonArray(String endpoint, String key) throws Exception{
        JSONArray allData = new JSONArray();
        JSONParser parser = new JSONParser();

        // Attempt to call fetch until we cannot and store the result in a single array
        int currentPage = 1;
        while(true){
            String pageURL = endpoint + "?page=" + currentPage;
            BufferedReader reader = fetchData(pageURL);

            JSONObject json = (JSONObject)parser.parse(reader);
            JSONArray pageData = (JSONArray)json.get(key);

            if (pageData == null || pageData.isEmpty()) {
                break; // Break the loop if no data is returned
            }

            allData.addAll(pageData);
            currentPage++;
        }

        return allData;
    }
    // Go through every character and load them into our characterAll dictionary, also adding all its sub values
    public void processCharacterJson(JSONArray charactersJsonArray){
        for (Object object : charactersJsonArray) {
            JSONObject charJson = (JSONObject) object;

            int cId = ((Number)charJson.get("id")).intValue();
            Character character = Character.get(cId);
            // Technically character should ALWAYS be null, unless the api has duplicates
            if(character == null){
                // get character data
                String cName = String.valueOf(charJson.get("name"));
                character = new Character(cId, cName);

                handleJutsu((JSONArray)charJson.get("jutsu"), character);
                handleUniqueTraits((JSONArray)charJson.get("uniqueTraits"), character);
                handleDebut((JSONObject) charJson.get("debut"), character);
                handleVoiceActors((JSONObject) charJson.get("voiceActors"), character);
                handleTools((JSONArray) charJson.get("tools"), character);
                handleNatureTypes((JSONArray) charJson.get("natureType"),character);

                if (!(cId == 22 || cId == 32 || cId == 342 || cId == 395 || cId == 431 || cId == 525 || cId == 773 || cId == 881 || cId == 1126 || cId == 1207 || cId == 1250)) {
                    handlePersonal((JSONObject) charJson.get("personal"), character);
                }

                Character.put(cId, character);
            }
        }
    }

    // This function is essentially the exact same as character
    // The only change is for jinchūriki
    public void processTailedBeastJson(JSONArray tailedBeastsJsonArray){
        for (Object object : tailedBeastsJsonArray) {
            JSONObject beastJson = (JSONObject) object;

            int beastId = ((Number)beastJson.get("id")).intValue();
            TailedBeast tailedBeast = TailedBeast.get(beastId);
            // Technically tailedBeast should ALWAYS be null, unless the api has duplicates
            if(tailedBeast == null){
                // get tailedBeast data
                String beastName = String.valueOf(beastJson.get("name"));
                tailedBeast = new TailedBeast(beastId, beastName);

                handleJutsu((JSONArray)beastJson.get("jutsu"), tailedBeast);
                handleUniqueTraits((JSONArray)beastJson.get("uniqueTraits"),  tailedBeast);
                handleDebut((JSONObject) beastJson.get("debut"), tailedBeast);
                handlePersonal((JSONObject) beastJson.get("personal"), tailedBeast);
                handleVoiceActors((JSONObject) beastJson.get("voiceActors"), tailedBeast);
                handleTools((JSONArray) beastJson.get("tools"), tailedBeast);
                handleNatureTypes((JSONArray) beastJson.get("natureType"), tailedBeast);

                TailedBeast.put(beastId, tailedBeast);
            }
        }
    }

    public void processClanJson(JSONArray clansJsonArray){
        for(Object object : clansJsonArray) {
            JSONObject clanJson = (JSONObject) object;

            int cId = ((Number)clanJson.get("id")).intValue();
            Clan clan = Clan.get(cId);

            if(clan == null){
                String cName = String.valueOf(clanJson.get("name"));
                clan = new Clan(cId, cName);
                handleAddingCharacters((JSONArray)clanJson.get("characters"), clan);
                Clan.put(cId, clan);
            }
        }
    }

    public void processKekkiGenkaiJson(JSONArray kekkiJsonArray){
        for(Object object : kekkiJsonArray) {
            JSONObject kekkiJson = (JSONObject) object;

            int kekkiId = ((Number)kekkiJson.get("id")).intValue();
            KekkeiGenkai kekki = KekkeiGenkai.get(kekkiId);

            if(kekki == null){
                String kekkiName = String.valueOf(kekkiJson.get("name"));
                kekki = new KekkeiGenkai(kekkiId, kekkiName);
                handleAddingCharacters((JSONArray)kekkiJson.get("characters"), kekki);
                KekkeiGenkai.put(kekkiId, kekki);
            }
        }
    }

    public void processTeamJson(JSONArray teamJsonArray){
        for(Object object : teamJsonArray) {
            JSONObject teamJson = (JSONObject) object;

            int teamId = ((Number)teamJson.get("id")).intValue();
            Team team = Team.get(teamId);

            if(team == null){
                String teamName = String.valueOf(teamJson.get("name"));
                team = new Team(teamId, teamName);
                handleAddingCharacters((JSONArray)teamJson.get("characters"), team);
                Team.put(teamId, team);
            }
        }
    }

    public void processVillageJson(JSONArray villageJsonArray){
        for(Object object : villageJsonArray) {
            JSONObject villageJson = (JSONObject) object;

            int villageId = ((Number)villageJson.get("id")).intValue();
            Village village = Village.get(villageId);

            if(village == null){
                String villageName = String.valueOf(villageJson.get("name"));
                village = new Village(villageId, villageName);
                handleAddingCharacters((JSONArray)villageJson.get("characters"), village);
                Village.put(villageId, village);
            }
        }
    }

    public void processKaraJson(JSONArray karaJsonArray){
        for(Object object : karaJsonArray) {
            JSONObject karaJson = (JSONObject) object;

            int karaId = ((Number)karaJson.get("id")).intValue();
            Kara kara = Kara.get(karaId);
            if(kara == null){
                //clan = new Clan();
                String karaName = String.valueOf(karaJson.get("name"));
                kara = new Kara(karaId, karaName);

                handleDebut((JSONObject) karaJson.get("debut"), kara);
                handlePersonal((JSONObject) karaJson.get("personal"), kara);
                handleTools((JSONArray) karaJson.get("tools"), kara);

                // KekkiGenkai STRANGE ADD
                String kekkiName = String.valueOf(karaJson.get("kekkeiGenkai"));
                KekkeiGenkai kekki = KekkeiGenkai.get(kekkiName);
                if(kekki != null){
                    kara.addKekkiGenkai(kekki);
                }

                Kara.put(karaId, kara);
            }
        }
    }

    public void processAkatsukiJson(JSONArray AkatsukiJsonArray){
        for(Object object : AkatsukiJsonArray) {
            JSONObject akatsukiJson = (JSONObject) object;

            int akatsukiId = ((Number)akatsukiJson.get("id")).intValue();
            Akatsuki akatsuki = Akatsuki.get(akatsukiId);
            if(akatsuki == null){
                String akatsukiName = String.valueOf(akatsukiJson.get("name"));
                akatsuki = new Akatsuki(akatsukiId, akatsukiName);

                //debut
                //jutsu
                //natureType
                //personal
                // tailed beast
                // Partner (s)?
                //uniquetraits
                //voiceactors

                Akatsuki.put(akatsukiId, akatsuki);
            }
        }
    }
    public void handleVoiceActors(JSONObject voiceActorsJson, IHasVoiceActor target){
        if (voiceActorsJson == null) return;

        // DIFFERENT LANGUAGES
        for (Object languageObj : voiceActorsJson.keySet()) {
            String language = (String) languageObj;
            Object actor = voiceActorsJson.get(language);

            // SINGLE CASE
            if (actor instanceof String) {
                String uniqueKey = actor + language;
                VoiceActor voiceActor = VoiceActor.get(uniqueKey);
                if (voiceActor == null) {
                    voiceActor = new VoiceActor((String) actor, language);
                    VoiceActor.put(uniqueKey, voiceActor);
                }
                target.addActor(voiceActor);
            }
            // MULTIPLE ACTORS FOR GIVEN LANGUAGE
            else if (actor instanceof JSONArray) {

                for (Object nameObj : (JSONArray) actor) {
                    // We need to make sure every one is a separate value
                    String name = (String) nameObj;
                    String uniqueKey = name + language;
                    VoiceActor voiceActor = VoiceActor.get(uniqueKey);
                    if (voiceActor == null) {
                        voiceActor = new VoiceActor(name, language);
                        VoiceActor.put(uniqueKey, voiceActor);
                    }
                    target.addActor(voiceActor);
                }
            }
        }
    }

    public void handleNatureTypes(JSONArray natureTypeArray, IHasNatureType target){
        if (natureTypeArray == null) return;

        for (Object natureObj : natureTypeArray) {
            String nature = (String) natureObj;

            NatureType natureType = NatureType.get(nature);
            if (natureType == null) {
                natureType = new NatureType(nature);
                NatureType.put(nature, natureType);
            }
            target.addNatureType(natureType);
        }
    }

    public void handleUniqueTraits(JSONArray uniqueTraitsArray, IHasUniqueTraits target){
        if (uniqueTraitsArray == null) return;

        for (Object traitObj : uniqueTraitsArray) {
            String traitJson = (String)traitObj; // I dont know if I can directly convert to string?

            UniqueTrait trait = UniqueTrait.get(traitJson);
            if(trait == null){
                trait = new UniqueTrait(traitJson);
                UniqueTrait.put(traitJson, trait);
            }
            target.addTrait(trait);
        }
    }

    public void handleJutsu(JSONArray jutsuArray, IHasJutsu target){
        if(jutsuArray == null) return;

        for (Object jutsuObj : jutsuArray) {
            String jutsuJson = (String)jutsuObj;

            String jName = jutsuJson;
            //Jutsu jutsu = jutsusAll.get(jutsuJson);
            Jutsu jutsu = Jutsu.get(jutsuJson);
            if(jutsu == null){
                jutsu = new Jutsu(jName);
                //jutsusAll.put(jName, jutsu); // Add to map containing all jutsu
                Jutsu.put(jName, jutsu);
            }

            target.addJutus(jutsu);// Add jutsu to character
        }
    }

    // Assume all characters already have been loaded. YES!
    public void handleAddingCharacters(JSONArray characterArray, IHasCharacters target){
        if(characterArray == null) return;

        for (Object charObj : characterArray) {
            JSONObject charJson = (JSONObject)charObj;

            int charId = ((Number)charJson.get("id")).intValue();

            Character character = Character.get(charId);
            if(character == null){
                System.out.println("Something is wrong, creating(doing nothing) character outside of characters");
            }

            target.addCharacter(character);
        }
    }

    public void handleTools(JSONArray toolsJsonArray, IHasTools target){
        if (toolsJsonArray == null) return;

        for (Object toolObj : toolsJsonArray) {
            String toolName = (String) toolObj;

            Tool tool = Tool.get(toolName);
            if (tool == null) {
                tool = new Tool(toolName);
                Tool.put(toolName, tool);
            }
            target.addTool(tool);
        }
    }

    public void handleDebut(JSONObject debutJson, IHasDebut target){
        if (debutJson == null) return;

        for (Object key : debutJson.keySet()) {
            String mediaType = (String) key;
            String mediaName = debutJson.get(key).toString();
            //Media media = new Media(mediaType, mediaName);
            Media media = Media.get(mediaType+mediaName);
            if(media == null){
                media = new Media(mediaType, mediaName);
                Media.put(mediaType + mediaName, media);
            }
            target.addMedia(media);
        }
    }

    // TODO: FINE TUNE
    public void handlePersonal(JSONObject personalJson, IHasPersonal target){
        if (personalJson == null) return;

        for (Object keyObj : personalJson.keySet()) {
            String key = (String) keyObj; // Convert key to String
            Object value = personalJson.get(key);

            // THE ONLY DIFFERENCE FROM CHARACTER
            if("jinchūriki".equals(key) && target instanceof TailedBeast){
                JSONArray jinchurikiArray = (JSONArray) value;
                if (jinchurikiArray != null) {
                    for (Object jinchurikiNameObj : jinchurikiArray) {
                        String jinchurikiName = (String) jinchurikiNameObj;
                        Character jinchurikiCharacter = Character.get(jinchurikiName);
                        if (jinchurikiCharacter != null) {
                            ((TailedBeast)target).jinchuriki.add(jinchurikiCharacter);
                        }
                    }
                }
            }
            // MULTIPLE HEIGHTS. Just store it strangely as OOP, when converting to sql, we will fix it
            else if ("height".equals(key) && value instanceof JSONObject heightJson) {
                for (Object heightKeyObj : heightJson.keySet()) {
                    String heightKey = (String) heightKeyObj; // Convert heightKey to String
                    String heightValue = heightJson.get(heightKey).toString();
                    String heightUniqueKey = key + heightKey + heightValue;

                    Personal heightPersonal = Personal.get(heightUniqueKey);
                    if (heightPersonal == null) {
                        heightPersonal = new Personal(key + " (" + heightKey + ")", heightValue);
                        Personal.put(heightUniqueKey, heightPersonal);
                    }

                    target.addPersonal(heightPersonal);
                }
            }
            else {
                // STANDARD
                String uniqueKey = key + value.toString();
                Personal personal = Personal.get(uniqueKey);
                if (personal == null) {
                    personal = new Personal(key, value.toString());
                    Personal.put(uniqueKey, personal);
                }

                target.addPersonal(personal);
            }
        }

    }

    // template
    public void processJson(JSONArray clansJsonArray){
        for(Object object : clansJsonArray) {
            JSONObject clanJson = (JSONObject) object;

            int cId = ((Number)clanJson.get("id")).intValue();
            Clan clan = Clan.get(cId);
            if(clan == null){
                //clan = new Clan();

                // Clan info

                Clan.put(cId, clan);
            }
        }
    }
}
