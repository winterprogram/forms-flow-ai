import os
from dotenv import find_dotenv, load_dotenv

# this will load all the envars from a .env file located in the project root (api)
load_dotenv(find_dotenv())

FORMSFLOW_API_CORS_ORIGINS = os.getenv("FORMSFLOW_API_CORS_ORIGINS")
ALLOW_ALL_ORIGINS = "*"
CORS_ORIGINS = []
if FORMSFLOW_API_CORS_ORIGINS != "*":
    CORS_ORIGINS = FORMSFLOW_API_CORS_ORIGINS.split(",")
DESIGNER_GROUP = "formsflow-designer"
REVIEWER_GROUP = "formsflow-reviewer"
ALLOW_ALL_APPLICATIONS = "/formsflow/formsflow-reviewer/access-allow-applications"

NEW_APPLICATION_STATUS = "New"
KEYCLOAK_DASHBOARD_BASE_GROUP = "formsflow-analytics"
