package com.rewards_service.infrastructure.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Mini Rewards Service</title>
                <style>
                    * {
                        margin: 0;
                        padding: 0;
                        box-sizing: border-box;
                    }

                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        min-height: 100vh;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        color: #333;
                    }

                    .container {
                        background: rgba(255, 255, 255, 0.95);
                        border-radius: 20px;
                        padding: 40px;
                        box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
                        max-width: 900px;
                        width: 95%;
                        text-align: center;
                        backdrop-filter: blur(10px);
                        margin: 20px;
                    }

                    .header {
                        margin-bottom: 30px;
                    }

                    .logo {
                        font-size: 3rem;
                        color: #667eea;
                        margin-bottom: 10px;
                        text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
                    }

                    .subtitle {
                        font-size: 1.2rem;
                        color: #666;
                        margin-bottom: 20px;
                    }

                    .description {
                        font-size: 1rem;
                        color: #777;
                        margin-bottom: 30px;
                        line-height: 1.6;
                    }

                    .endpoints {
                        display: grid;
                        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
                        gap: 30px;
                        margin-bottom: 30px;
                    }

                    .endpoint-group {
                        background: #f8f9fa;
                        border-radius: 12px;
                        padding: 20px;
                        border-left: 4px solid #667eea;
                        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
                    }

                    .endpoint-group h3 {
                        color: #667eea;
                        margin-bottom: 15px;
                        font-size: 1.2rem;
                        border-bottom: 2px solid #667eea;
                        padding-bottom: 8px;
                        display: flex;
                        align-items: center;
                        gap: 8px;
                    }

                    .endpoint {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        padding: 12px 15px;
                        margin-bottom: 10px;
                        background: white;
                        border-radius: 8px;
                        border: 1px solid #e9ecef;
                        transition: all 0.3s ease;
                    }

                    .endpoint:hover {
                        background: #f8f9fa;
                        transform: translateY(-2px);
                        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                    }

                    .endpoint-method {
                        font-weight: bold;
                        color: #28a745;
                        background: rgba(40, 167, 69, 0.1);
                        padding: 4px 8px;
                        border-radius: 4px;
                        font-size: 0.9rem;
                        border: 1px solid rgba(40, 167, 69, 0.2);
                    }

                    .endpoint-url {
                        font-family: 'Courier New', monospace;
                        color: #495057;
                        font-size: 0.95rem;
                        font-weight: 500;
                    }

                    .link-button {
                        display: inline-block;
                        padding: 12px 24px;
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        color: white;
                        text-decoration: none;
                        border-radius: 25px;
                        font-weight: 600;
                        transition: all 0.3s ease;
                        margin: 10px;
                        box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
                    }

                    .link-button:hover {
                        transform: translateY(-2px);
                        box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
                    }

                    .auth-note {
                        background: #fff3cd;
                        border: 1px solid #ffeaa7;
                        border-radius: 8px;
                        padding: 15px;
                        margin-top: 20px;
                        color: #856404;
                    }

                    .auth-note strong {
                        color: #533f00;
                    }

                    /* Large screens */
                    @media (min-width: 1200px) {
                        .container {
                            max-width: 1100px;
                            padding: 50px;
                        }

                        .endpoints {
                            grid-template-columns: repeat(3, 1fr);
                            gap: 40px;
                        }

                        .logo {
                            font-size: 3.5rem;
                        }
                    }

                    /* Medium screens (tablets) */
                    @media (max-width: 1024px) {
                        .container {
                            max-width: 800px;
                            padding: 35px;
                        }

                        .endpoints {
                            grid-template-columns: repeat(2, 1fr);
                            gap: 25px;
                        }
                    }

                    /* Small tablets and large phones */
                    @media (max-width: 768px) {
                        .container {
                            padding: 25px;
                            margin: 15px;
                            width: calc(100% - 30px);
                        }

                        .logo {
                            font-size: 2.8rem;
                        }

                        .subtitle {
                            font-size: 1.1rem;
                        }

                        .endpoints {
                            grid-template-columns: 1fr;
                            gap: 20px;
                        }

                        .endpoint-group {
                            padding: 15px;
                        }

                        .endpoint {
                            flex-direction: column;
                            align-items: flex-start;
                            gap: 8px;
                            text-align: left;
                            padding: 10px 12px;
                        }

                        .endpoint-method {
                            align-self: flex-end;
                            font-size: 0.85rem;
                        }

                        .endpoint-url {
                            font-size: 0.9rem;
                        }
                    }

                    /* Small phones */
                    @media (max-width: 480px) {
                        .container {
                            padding: 20px;
                            margin: 10px;
                            width: calc(100% - 20px);
                        }

                        .logo {
                            font-size: 2.5rem;
                        }

                        .header h1 {
                            font-size: 1.8rem;
                        }

                        .subtitle {
                            font-size: 1rem;
                        }

                        .description {
                            font-size: 0.95rem;
                        }

                        .endpoint-group h3 {
                            font-size: 1.1rem;
                        }

                        .link-button {
                            padding: 10px 20px;
                            font-size: 0.9rem;
                        }

                        .auth-note {
                            padding: 12px;
                            font-size: 0.9rem;
                        }
                    }

                    /* Extra small phones */
                    @media (max-width: 360px) {
                        .container {
                            padding: 15px;
                            margin: 5px;
                        }

                        .logo {
                            font-size: 2.2rem;
                        }

                        .header h1 {
                            font-size: 1.6rem;
                        }

                        .endpoint-group {
                            padding: 12px;
                        }

                        .link-button {
                            padding: 8px 16px;
                            font-size: 0.85rem;
                        }
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <div class="logo">🎁</div>
                        <h1>Mini Rewards Service</h1>
                        <p class="subtitle">Clean Architecture • Spring Boot • Enterprise Ready</p>
                    </div>

                    <p class="description">
                        A comprehensive rewards system built with Clean Architecture principles,
                        featuring Redis caching, RabbitMQ messaging, and enterprise-grade security.
                    </p>

                    <div class="endpoints">
                        <div class="endpoint-group">
                            <h3>📚 Documentation</h3>
                            <div class="endpoint">
                                <span class="endpoint-url">/swagger-ui.html</span>
                                <a href="/swagger-ui.html" class="link-button">Open Swagger UI</a>
                            </div>
                            <div class="endpoint">
                                <span class="endpoint-url">/v3/api-docs</span>
                                <span class="endpoint-method">GET</span>
                            </div>
                        </div>

                        <div class="endpoint-group">
                            <h3>🔍 Monitoring</h3>
                            <div class="endpoint">
                                <span class="endpoint-url">/actuator/health</span>
                                <span class="endpoint-method">GET</span>
                            </div>
                            <div class="endpoint">
                                <span class="endpoint-url">/actuator/metrics</span>
                                <span class="endpoint-method">GET</span>
                            </div>
                        </div>

                        <div class="endpoint-group">
                            <h3>🎯 API Endpoints</h3>
                            <div class="endpoint">
                                <span class="endpoint-url">/api/rewards/award</span>
                                <span class="endpoint-method">POST</span>
                            </div>
                            <div class="endpoint">
                                <span class="endpoint-url">/api/rewards/redeem</span>
                                <span class="endpoint-method">POST</span>
                            </div>
                            <div class="endpoint">
                                <span class="endpoint-url">/api/rewards/balance/{userId}</span>
                                <span class="endpoint-method">GET</span>
                            </div>
                        </div>
                    </div>

                    <div class="auth-note">
                        <strong>🔐 Authentication Required:</strong> All API endpoints require an
                        <code>X-API-Key</code> header for authentication. Use Swagger UI to test with proper authentication.
                    </div>
                </div>
            </body>
            </html>
            """;
    }
}