const Constant = {
  API_URL: "/api",
};

export const QueryKey = {
  ADMIN: "admin",
  USER: "user",
  PROBLEMS: "probelms",
  TOPICS: "topics",
  CODES: "codes",
  TESTCASES: "tcases",
  SUBMISSIONS: "submissions",
} as const;

export const REGEX = {
  UUID: /^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/i,
};

export default Constant;
