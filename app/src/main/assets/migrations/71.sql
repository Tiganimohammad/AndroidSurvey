ALTER TABLE NextQuestions ADD COLUMN Deleted BOOLEAN;
ALTER TABLE MultipleSkips ADD COLUMN Deleted BOOLEAN;
ALTER TABLE NextQuestions ADD COLUMN Value STRING;
ALTER TABLE NextQuestions ADD COLUMN CompleteSurvey BOOLEAN;
ALTER TABLE OptionInOptionSets ADD COLUMN IsExclusive BOOLEAN;
ALTER TABLE AdminSettings ADD COLUMN DatabaseVersion INTEGER;