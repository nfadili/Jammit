package nfadili.tacoma.uw.edu.jammit.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample username for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class SearchContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<SearchedProfileItem> ITEMS = new ArrayList<SearchedProfileItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, SearchedProfileItem> ITEM_MAP = new HashMap<String, SearchedProfileItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(SearchedProfileItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.username, item);
    }

    private static SearchedProfileItem createDummyItem(int position) {
        return new SearchedProfileItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of username.
     */
    public static class SearchedProfileItem {
        public final String username;
        public final String age;
        public final String city;

        public SearchedProfileItem(String username, String age, String city) {
            this.username = username;
            this.age = age;
            this.city = city;
        }

        @Override
        public String toString() {
            return username;
        }
    }
}