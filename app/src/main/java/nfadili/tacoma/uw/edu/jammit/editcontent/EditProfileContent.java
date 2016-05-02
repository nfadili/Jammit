package nfadili.tacoma.uw.edu.jammit.editcontent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class EditProfileContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ProfileParameter> ITEMS = new ArrayList<ProfileParameter>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, ProfileParameter> ITEM_MAP = new HashMap<Integer, ProfileParameter>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
        addItem(new ProfileParameter(1,"Image","Change your profile picture."));
        addItem(new ProfileParameter(2,"Name","Update your name."));
        addItem(new ProfileParameter(3,"Instruments","List the instruments that you can play."));
        addItem(new ProfileParameter(4,"Styles","List the musical styles you can play."));
        addItem(new ProfileParameter(5,"Age","Update your age.."));
        addItem(new ProfileParameter(6,"Bio","Write a little bit about yourself."));
        addItem(new ProfileParameter(7,"City","Update your city."));
    }

    private static void addItem(ProfileParameter item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

//    private static ProfileParameter createDummyItem(int position) {
//        return new ProfileParameter(String.valueOf(position), "Item " + position, makeDetails(position));
//    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ProfileParameter {
        public final int id;
        public final String content;
        public final String details;

        public ProfileParameter(int id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
