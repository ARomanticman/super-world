CREATE TABLE "test_flyway" (
     "id" VARCHAR(32) NOT NULL,
     "type" VARCHAR(64) NOT NULL,
     "name" VARCHAR(64) NOT NULL,
     "code" INT NOT NULL,
     PRIMARY KEY ("id")
);
COMMENT ON TABLE "enum_data" IS '枚举表';
COMMENT ON COLUMN "enum_data"."type" IS '类型';
COMMENT ON COLUMN "enum_data"."name" IS '名称';
COMMENT ON COLUMN "enum_data"."code" IS '值';