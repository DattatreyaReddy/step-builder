package com.padya.stepbuilder;

public class Message {
    String id;

    String groupId;

    Long dateCreated;

    Long lastModified;

    String message;

    public Message(String id, String groupId, Long dateCreated, Long lastModified, String message) {
        this.id = id;
        this.groupId = groupId;
        this.dateCreated = dateCreated;
        this.lastModified = lastModified;
        this.message = message;
    }

    public static interface IdStep {
        GroupIdStep withId(String id);
    }

    public static interface GroupIdStep {
        DateCreatedStep withGroupId(String groupId);
    }

    public static interface DateCreatedStep {
        LastModifiedStep withDateCreated(Long dateCreated);
    }

    public static interface LastModifiedStep {
        MessageStep withLastModified(Long lastModified);
    }

    public static interface MessageStep {
        BuildStep withMessage(String message);
    }

    public static interface BuildStep {
        Message build();
    }

    public static class Builder
        implements IdStep, GroupIdStep, DateCreatedStep, LastModifiedStep, MessageStep, BuildStep {
        private String id;

        private String groupId;

        private Long dateCreated;

        private Long lastModified;

        private String message;

        private Builder() {
        }

        public static IdStep message() {
            return new Builder();
        }

        @Override
        public GroupIdStep withId(String id) {
            this.id = id;
            return this;
        }

        @Override
        public DateCreatedStep withGroupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        @Override
        public LastModifiedStep withDateCreated(Long dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        @Override
        public MessageStep withLastModified(Long lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        @Override
        public BuildStep withMessage(String message) {
            this.message = message;
            return this;
        }

        @Override
        public Message build() {
            return new Message(
                this.id,
                this.groupId,
                this.dateCreated,
                this.lastModified,
                this.message
            );
        }
    }
}
