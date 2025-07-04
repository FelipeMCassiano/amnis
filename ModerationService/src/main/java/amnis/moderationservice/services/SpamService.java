package amnis.moderationservice.services;

import amnis.moderationservice.models.BlacklistMessages;
import amnis.moderationservice.models.events.ChatMessageSentEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

public class SpamService {
    private final Set<String> blackListKeywordsList = new HashSet<>();
    private final BlacklistMessages blacklistMessages;
    public SpamService(BlacklistMessages blacklistMessages) {
        this.blacklistMessages = blacklistMessages;
        List<String> blackListKeywords = blacklistMessages.getBlackListKeywords();

        if(blackListKeywords != null && !blackListKeywords.isEmpty()) {
            blackListKeywords.forEach(keyword -> blackListKeywordsList.add(keyword.toLowerCase()));
        }
    }
    public boolean isSpam(String message) {
        message = message.toLowerCase();
        for (String keyword : blackListKeywordsList) {
            if (message.contains(keyword)) {
                learnFromMessage(message);
                return true;
            }
        }
        return false;
    }

    public void learnFromMessage(String message) {
        String[] words = message.split("\\W+");
        for (String word : words) {
            if (word.length() >= 6 && !blackListKeywordsList.contains(word)) {
                blacklistMessages.addBlackListKeyword(word);
            }
        }

    }
}